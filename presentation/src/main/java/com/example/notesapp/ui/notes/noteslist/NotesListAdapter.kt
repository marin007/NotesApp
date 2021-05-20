package com.example.notesapp.ui.notes.noteslist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Note
import com.example.notesapp.R


class NotesListAdapter(
    private val notes: MutableList<Note>,
    private val action: (noteId: Int, noteText: String, picturePath: String?) -> Unit
) : RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text: String? = notes[position].note

        val imagePath: String? = notes[position].picture

        notes[position].picture?.let {
            try {
                val thumbnail = getThumbnail(it)
                holder.imageViewPicture.clipToOutline = true
                holder.imageViewPicture.setImageBitmap(thumbnail)
            } catch (ex: Exception) {}
        }
        holder.textViewDesc.text = text
        holder.constraintLayoutWrapper.setOnClickListener {
            notes[position].id?.let {
                action(it, holder.textViewDesc.text.toString(), imagePath)
            }
        }
    }

    override fun getItemCount(): Int = notes.size

    fun removeAt(position: Int) {
        if (position < itemCount) {
            notes.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
            notifyDataSetChanged()
        }
    }

    fun getItemByPosition(position: Int): Note? {
        return if (position < itemCount) {
            notes[position]
        } else {
            null
        }
    }

    private fun getThumbnail(path: String): Bitmap {
        val exif = ExifInterface(path)
        val imageData: ByteArray = exif.thumbnail
        return BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewDesc: TextView =
            view.findViewById(R.id.textViewDescription)

        val imageViewPicture: ImageView =
            view.findViewById(R.id.imageViewPicture)

        val constraintLayoutWrapper: ConstraintLayout =
            view.findViewById(R.id.constraintLayoutWrapper)
    }
}