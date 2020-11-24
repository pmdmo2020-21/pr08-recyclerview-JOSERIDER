package es.iessaladillo.pedrojoya.pr06.ui.users

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UserActivityBinding
import es.iessaladillo.pedrojoya.pr06.databinding.UsersActivityBinding
import es.iessaladillo.pedrojoya.pr06.ui.add_user.AddUserActivity
import es.iessaladillo.pedrojoya.pr06.ui.edit_user.EditUserActivity
import es.iessaladillo.pedrojoya.pr06.utils.observeEvent
import es.iessaladillo.pedrojoya.pr06.utils.requireParcelableExtra

class UsersActivity : AppCompatActivity() {


    private val binding: UsersActivityBinding by lazy {
        UsersActivityBinding.inflate(layoutInflater)
    }

    private val viewModel: UsersActivityViewModel by viewModels {
        UsersActivityViewModelFactory(Database)
    }


    private val usersAdapter: UsersAdapter by lazy {
        UsersAdapter().apply {
            onEditUser = { pos -> editUser(currentList[pos]) }
            onDeleteUser = { pos -> deleteUser(currentList[pos]) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeLiveData()
        observeEvents()
        setupViews()
    }


    // NO TOCAR: Estos métodos gestionan el menú y su gestión
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.users, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuAdd) {
            onAddUser()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    // FIN NO TOCAR

    private fun observeLiveData() {
        observeUserList()
        observeEmptyView()
    }


    private fun setupViews() {
        setupUsersRcl()
    }

    private fun setupUsersRcl() {
        binding.usersRcl.run {
            layoutManager = GridLayoutManager(this@UsersActivity,
                    resources.getInteger(R.integer.users_grid_columns), RecyclerView.VERTICAL, false)

            itemAnimator = DefaultItemAnimator()
            adapter = usersAdapter
        }
    }


    private fun editUser(user: User) = viewModel.updateUser(user)

    private fun deleteUser(user: User) = viewModel.deleteUser(user)

    private fun onAddUser() = viewModel.navigateToAddUser()


    //---- Observers ---
    private fun observeUserList() {
        viewModel.userList.observe(this, Observer {
            usersAdapter.submitList(it)
        })
    }

    private fun observeEmptyView() {
        viewModel.viewGoneUnless.observe(this, Observer {
            binding.usersLblEmptyView.visibility = it
        })
    }


    private fun observeEvents() {
        viewModel.onNavigateAddUser.observeEvent(this) {
            startActivity(AddUserActivity.newIntent(this))
        }
        viewModel.onNavigateEditUser.observeEvent(this) { user ->
            startActivity(EditUserActivity.newIntent(this, user))
        }
    }
}