package es.iessaladillo.pedrojoya.pr06.utils

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlin.random.Random

fun IntRange.random(): Int = Random.nextInt(start, endInclusive + 1)

fun View.hideSoftKeyboard(): Boolean {
    val imm = context.getSystemService(
            Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return imm?.hideSoftInputFromWindow(windowToken, 0) ?: false
}


fun Intent.requireParcelableExtra(extraId:String):Any? {
    if (!hasExtra(extraId))  throw RuntimeException("Intent is not valid, must have  EXTRA_POKEMON")
    return getParcelableExtra(extraId)
}