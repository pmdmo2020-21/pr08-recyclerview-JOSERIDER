package es.iessaladillo.pedrojoya.pr06.utils

import android.text.Editable


fun isValidPhoneNumber(phone:Editable): Boolean {
    val phone = phone.toString()
    return (phone.startsWith("6") || phone.startsWith("7")) && phone.length == 9
}

fun isValidEmail(email:Editable): Boolean {
    val email = email.toString()
    val regex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex()
    return regex matches email
}

fun isValidName(name:Editable): Boolean {
    val name = name.toString()
    return name.isNotBlank() || name.isNotEmpty()
}

