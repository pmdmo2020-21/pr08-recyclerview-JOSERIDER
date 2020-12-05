package es.iessaladillo.pedrojoya.pr06.utils

import android.text.Editable


fun isValidPhoneNumber(phone:String): Boolean {
    val phone = phone
    return (phone.startsWith("6") || phone.startsWith("7")) && phone.length == 9
}

fun isValidEmail(email:String): Boolean {
    val email = email
    val regex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex()
    return regex matches email
}

fun isValidName(name:String): Boolean {
    val name = name
    return name.isNotBlank() || name.isNotEmpty()
}

