package es.iessaladillo.pedrojoya.pr06.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.pr06.data.DataSource

@Suppress("UNCHECKED_CAST")
class UsersActivityViewModelFactory(private val repository:DataSource):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = UsersActivityViewModel(repository) as T
}