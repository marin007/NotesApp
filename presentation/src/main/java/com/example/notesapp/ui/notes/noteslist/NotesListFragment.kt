package com.example.notesapp.ui.notes.noteslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Note
import com.example.notesapp.R
import com.example.notesapp.common.*
import org.koin.androidx.viewmodel.ext.android.getSharedViewModel
import com.example.notesapp.databinding.NotesFragmentBinding
import com.example.notesapp.ui.notes.NotesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class NotesListFragment : BaseFragment<NotesFragmentBinding, NotesViewModel>() {


    override fun viewModel(): NotesViewModel = getSharedViewModel()

    override fun binding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): NotesFragmentBinding {
        return NotesFragmentBinding.inflate(inflater, container, false)
    }

    override fun subscribeToUiChanges() { }

    override fun subscribeToEvents() {
        viewModel.navigation.observeEvent(viewLifecycleOwner) {
            when (it) {
                0 -> findNavController().popBackStack()
                else -> {
                    findNavController().navigate(it)
                }
            }
        }
    }

    override fun prepareUi() {
        val swipeHandler = object : SwipeToDelete(secureContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = viewModel.listAdapter.getItemByPosition(viewHolder.adapterPosition)
                note?.picture?.let {
                    val isFileDeleted: Boolean = runBlocking(Dispatchers.IO) {
                        viewModel.deletePictureFile(it)
                    }
                    if(!isFileDeleted) {
                        showErrorToast(secureContext, "Failed to delete picture file")
                        return
                    }
                }
                 viewModel.listAdapter.removeAt(viewHolder.adapterPosition)
                note?.id?.let { viewModel.deleteNote(it) }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        val actionbar = (activity as AppCompatActivity?)?.supportActionBar
        actionbar?.title = getString(R.string.app_name)
        actionbar?.setDisplayHomeAsUpEnabled(false)
        binding.notesViewModel = viewModel
        binding.recyclerView.addItemDecoration(getRecycleViewDivider(secureContext))
        binding.recyclerView.setHasFixedSize(false)
        binding.recyclerView.layoutManager = LinearLayoutManager(secureContext)
        viewModel.notes.observe(this) {
            loadNotesData(it)
        }
        viewModel.clearNoteData()
    }

    private fun editNote(noteId: Int, note: String, picture: String? = null) {
        viewModel.noteId = noteId
        viewModel.noteText.set(note)
        viewModel.picturePath.set(picture)
        viewModel.moveToEditNotesScreen()
    }

    private fun loadNotesData(items: List<Note>) {
        val list = mutableListOf<Note>()
        list.addAll(items)
        viewModel.listAdapter = NotesListAdapter(list, ::editNote)
        binding.recyclerView.adapter = viewModel.listAdapter
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}