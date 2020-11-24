package es.iessaladillo.pedrojoya.pr06.ui.add_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.pr06.data.DataSource
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.utils.Event
import es.iessaladillo.pedrojoya.pr06.utils.random

// TODO:
//  Crear la clase EditUserViewModel. Ten en cuenta que la url de la photo
//  deberá ser preservada por si la actividad es destruida por falta de recursos.

private const val STATE_PHOTO_URL = "STATE_PHOTO_URL"

class AddUserViewModel(
        private val repository: DataSource,
        savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _photoUrl: MutableLiveData<String> = savedStateHandle.getLiveData(
            STATE_PHOTO_URL, getRandomPhotoUrl())

    val photoUrl: LiveData<String>
        get() = _photoUrl



    private val _onSaveUser: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val onSaveUser: LiveData<Event<Boolean>>
        get() = _onSaveUser

    private val _onShowSnackbar: MutableLiveData<Event<String>> = MutableLiveData()
    val onShowSnackbar: LiveData<Event<String>>
        get() = _onShowSnackbar


    // Para obtener un URL de foto de forma aleatoria (tendrás que definir
    // e inicializar el random a nivel de clase.
    private fun getRandomPhotoUrl(): String =
            "https://picsum.photos/id/${(0..100).random()}/400/300"


    fun changePhoto() {
        _photoUrl.value = getRandomPhotoUrl()
    }


    fun insertUser(user: User) {
        repository.insertUser(user)
        _onSaveUser.value = Event(true)
    }


    fun showSnackbar(message:String){
        _onShowSnackbar.value = Event(message)
    }




}
