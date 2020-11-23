package es.iessaladillo.pedrojoya.pr06.ui.users

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import es.iessaladillo.pedrojoya.pr06.data.DataSource
import es.iessaladillo.pedrojoya.pr06.data.model.User

class UsersActivityViewModel(private val repository:DataSource) :ViewModel(){


    val userList: LiveData<List<User>> = repository.getAllUsersOrderedByName()

    val viewGoneUnless:LiveData<Int> = userList.map {
        if (it.isEmpty()) View.VISIBLE else View.GONE
    }

    fun updateUser(user: User) = repository.updateUser(user)

    fun deleteUser(user: User) = repository.deleteUser(user)

}
