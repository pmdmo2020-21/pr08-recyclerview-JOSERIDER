package es.iessaladillo.pedrojoya.pr06.data

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

    private val userList: MutableList<User> = mutableListOf()

    private val _userList: MutableLiveData<List<User>> = MutableLiveData()

    private var userId: Long = 1

    override fun getAllUsersOrderedByName(): LiveData<List<User>> = _userList

    override fun insertUser(user: User) {
        val newUser = user.copy(id = userId)
        if (userList.add(newUser)) {
            updateLiveData()
            userId +=1
        }
    }

    override fun updateUser(user: User) {
        //Remove the oldest user
        userList.remove(userList.find { it.id == user.id })
        //Add new user
        userList.add(user)
        updateLiveData()

    }

    override fun deleteUser(user: User) {
        userList.remove(user)
        updateLiveData()
    }


    private fun updateLiveData() {
        _userList.value = getSortedList()
    }

    private fun getSortedList(): List<User> = userList.sortedBy { it.name }

}