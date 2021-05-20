package com.example.notesapp.ui.notes.notesedit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.common.BaseFragment
import com.example.notesapp.common.observeEvent
import com.example.notesapp.common.toEditable
import com.example.notesapp.databinding.EditNoteFragmentBinding
import com.example.notesapp.ui.notes.NotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import java.io.File
import java.io.IOException


class EditNoteFragment : BaseFragment<EditNoteFragmentBinding, NotesViewModel>() {


    override fun viewModel(): NotesViewModel = getSharedViewModel()

    override fun binding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): EditNoteFragmentBinding {
        return EditNoteFragmentBinding.inflate(inflater, container, false)
    }

    override fun subscribeToUiChanges() {

    }

    override fun subscribeToEvents() {
        viewModel.navigation.observeEvent(viewLifecycleOwner) {
            if (it == 0) {
                findNavController().popBackStack()
            } else {
                findNavController().navigate(it)
            }
        }
    }

    override fun prepareUi() {
        val actionbar = (activity as AppCompatActivity?)?.supportActionBar
        actionbar?.title = getString(R.string.put_a_note)
        actionbar?.setDisplayHomeAsUpEnabled(true)
        binding.notesViewModel = viewModel
        binding.imageButtonTakePicture.setOnClickListener { dispatchTakePictureIntent() }
        binding.shareButton.setOnClickListener {
            viewModel.noteText.get()?.let { note -> shareNote(note, viewModel.picturePath.get()) }
        }
        binding.saveButton.setOnClickListener {
            viewModel.noteText.get()?.let { note ->
                when (viewModel.noteId) {
                    0 -> viewModel.insertNote(
                        noteText = note,
                        picture = viewModel.picturePath.get()
                    )
                    else -> {
                        viewModel.updateNote(
                            noteText = note,
                            picture = viewModel.picturePath.get()
                        )
                    }
                }
            }
        }

        viewModel.picturePath.get()?.let {
            val bitmap = viewModel.getBitmapPicture(it)
            binding.imageButtonPicture.setImageBitmap(bitmap)
        }
        binding.noteEditText.text = viewModel.noteText.get()?.toEditable()
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun dispatchTakePictureIntent() {

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(secureContext.packageManager)?.also {
                val pictureFile: File? = runBlocking(Dispatchers.IO) {
                    try {
                        val storageDir: File? = secureContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                        viewModel.createFile(storageDir)
                    } catch (ex: IOException) {
                        null
                    }
                }
                pictureFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        secureContext,
                        "${requireContext().packageName}.fileprovider",
                        it
                    )
                    viewModel.picturePath.set(pictureFile.absolutePath)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun shareNote(note: String, filePath: String?) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, note)
        filePath?.let {
            val authority = "${requireContext().packageName}.fileprovider"
            val file = File(filePath)
            val uri = FileProvider.getUriForFile(requireContext(), authority, file)
            intent.type = "image/jpg"
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
        } ?: kotlin.run {
            intent.type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, ""))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
            viewModel.picturePath.get()?.let {
                val bitmap = viewModel.getBitmapPicture(it)
                binding.imageButtonPicture.setImageBitmap(bitmap)
            }
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
}