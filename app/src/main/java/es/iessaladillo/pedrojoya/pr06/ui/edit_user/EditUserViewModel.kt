package es.iessaladillo.pedrojoya.pr06.ui.edit_user

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.DataSource
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.utils.*

// TODO:
//  Crear la clase EditUserViewModel. Ten en cuenta que la url de la photo
//  deberá ser preservada por si la actividad es destruida por falta de recursos.

private const val STATE_USER = "STATE_USER"
private const val STATE_PHOTO_URL = "STATE_PHOTO_URL"

class EditUserViewModel(
        private val application: Application,
        private val repository: DataSource,
        savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _user: MutableLiveData<User> = savedStateHandle.getLiveData(STATE_USER)
    val user: LiveData<User>
        get() = _user

    private val _photoUrl: MutableLiveData<String> = savedStateHandle.getLiveData(
            STATE_PHOTO_URL)

    val photoUrl: LiveData<String>
        get() = _photoUrl


    //EVENTS
    private val _onEditUser: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val onEditUser: LiveData<Event<Boolean>>
        get() = _onEditUser

    private val _onShowSnackbar: MutableLiveData<Event<String>> = MutableLiveData()
    val onShowSnackbar: LiveData<Event<String>>
        get() = _onShowSnackbar


    private fun getRandomPhotoUrl(): String =
            "https://picsum.photos/id/${(0..100).random()}/400/300"


    fun setUser(user: User) {
        _user.value = user
    }

    fun updateUser(name: String, email: String, phone: String, address: String, web: String) {
        if (isValidForm(name, email, phone)) {
            val user = _user.value?.copy(name = name, email = email, phoneNumber = phone, address = address, web = web,
                    photoUrl = _photoUrl.value!!) ?: throw RuntimeException("User is null")

            repository.updateUser(user)
            _onEditUser.value = Event(true)
        } else {
            showSnackbar(application.getString(R.string.user_invalid_data))
        }
    }


    private fun isValidForm(name: String, email: String, phone: String): Boolean =
            isValidName(name) &&
                    isValidEmail(email) &&
                    isValidPhoneNumber(phone)


    fun changePhoto() {
        _photoUrl.value = getRandomPhotoUrl()
    }

    fun setPhoto(photoUrl: String) {
        _photoUrl.value = photoUrl
    }

    fun showSnackbar(message: String) {
        _onShowSnackbar.value = Event(message)
    }

}
