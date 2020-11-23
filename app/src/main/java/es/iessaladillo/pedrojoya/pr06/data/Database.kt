package es.iessaladillo.pedrojoya.pr06.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.iessaladillo.pedrojoya.pr06.data.model.User

// TODO:
//  Crear una singleton Database que implemente la interfaz DataSource.
//  Al insertar un usuario, se le asignará un id autonumérico
//  (primer valor válido será el 1) que deberá ser gestionado por la Database.
//  La base de datos debe ser reactiva, de manera que cuando se agrege,
//  actualice o borre un usuario, los observadores de la lista deberán
//  recibir la nueva lista.
//  Al consultar los usuario se deberá retornar un LiveData con la lista
//  de usuarios ordenada por nombre


object Database : DataSource {

    private val userList: MutableList<User> = mutableListOf(
            User(2,"Jose","Baldomero","55454353","","","")
    )

    private val _userList: MutableLiveData<List<User>> = MutableLiveData()

    private val userId: Long = 1

    override fun getAllUsersOrderedByName(): LiveData<List<User>> = _userList

    override fun insertUser(user: User) {
        val newUser = user.copy(id = userId)
        if (userList.add(newUser)) {
            updateLiveData()
            userId.inc()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun updateUser(user: User) {
        if(userList.removeIf { it.id == user.id }){
            userList.add(user)
            updateLiveData()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun deleteUser(user: User) {
        if (userList.removeIf { it.id == user.id }) updateLiveData()
    }


    private fun updateLiveData() {
        _userList.value = getSortedList()
    }

    private fun getSortedList(): List<User> = userList.sortedBy { it.name }

}