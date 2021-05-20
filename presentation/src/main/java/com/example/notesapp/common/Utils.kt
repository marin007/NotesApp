package com.example.notesapp.common

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.text.Editable
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.notesapp.R

//https://www.rockandnull.com/livedata-observe-once/
class ValueWrapper<T>(private val value: T) {

    private var isConsumed = false

    fun get(): T? =
        if (isConsumed) {
            null
        } else {
            isConsumed = true
            value
        }
}

inline fun <T> LiveData<ValueWrapper<T>>.observeEvent(owner: LifecycleOwner, crossinline onEventUnhandledContent: (T) -> Unit) {
    observe(owner, { it?.get()?.let(onEventUnhandledContent) })
}

fun showErrorToast(context: Context, message: String) {
    val toast: Toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
    val view = toast.view
    view?.background?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
    val text = view?.findViewById<TextView>(android.R.id.message)
    text?.setTextColor(Color.WHITE)
    toast.show()
}
fun getRecycleViewDivider(context: Context): DividerItemDecoration {
    val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    ContextCompat.getDrawable(context, R.drawable.row_separator)?.let {
        divider.setDrawable(it)
    }
    return divider
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
