package es.iessaladillo.pedrojoya.pr06.ui.users

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import es.iessaladillo.pedrojoya.pr06.data.DataSource
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.utils.Event

class UsersActivityViewModel(private val repository: DataSource) : ViewModel() {


    val userList: LiveData<List<User>> = repository.getAllUsersOrderedByName()

    val viewGoneUnless: LiveData<Int> = userList.map {
        if (it.isEmpty()) View.VISIBLE else View.GONE
    }
    private val _onNavigateAddUser: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val onNavigateAddUser: LiveData<Event<Boolean>>
        get() = _onNavigateAddUser

    private val _onNavigateEditUser:MutableLiveData<Event<User>> = MutableLiveData()
    val onNavigateEditUser:LiveData<Event<User>>
        get() = _onNavigateEditUser

    fun updateUser(user: User) {
        _onNavigateEditUser.value = Event( user)
    }

    fun deleteUser(user: User) = repository.deleteUser(user)

    fun navigateToAddUser(){
        _onNavigateAddUser.value = Event(true)
    }

}
