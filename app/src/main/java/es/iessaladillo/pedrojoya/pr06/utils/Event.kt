package es.iessaladillo.pedrojoya.pr06.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

// NO TOCAR

open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

}

inline fun <T> LiveData<Event<T>>.observeEvent(
        owner: LifecycleOwner, crossinline onEventUnhandledContent: (T) -> Unit) {
    observe(owner) { it.getContentIfNotHandled()?.let(onEventUnhandledContent) }
}