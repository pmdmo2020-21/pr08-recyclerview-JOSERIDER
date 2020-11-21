package es.iessaladillo.pedrojoya.pr06.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

// NO TOCAR: Esta extension function permite cargar una foto en un ImageView
// a partir de una URL.

fun ImageView.loadUrl(url: String) {
    Glide.with(this).load(url).into(this)
}