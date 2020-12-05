package es.iessaladillo.pedrojoya.pr06.ui.edit_user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.pedrojoya.pr06.R
import es.iessaladillo.pedrojoya.pr06.data.Database
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UserActivityBinding
import es.iessaladillo.pedrojoya.pr06.utils.*

class EditUserActivity : AppCompatActivity() {


    private val binding: UserActivityBinding by lazy {
        UserActivityBinding.inflate(layoutInflater)
    }

    private val viewModel: EditUserViewModel by viewModels {
        EditUserViewModelFactory(application, Database, this)
    }

    private val currentUser: User by lazy {
        viewModel.user.value!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getIntentData()
        observeLiveData()
        observeEvents()
        setupViews()

    }


    private fun observeLiveData() {
        viewModel.photoUrl.observe(this, Observer {
            binding.userImg.loadUrl(it)
        })
    }


    // NO TOCAR: Estos métodos gestionan el menú y su gestión

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.user, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuSave) {
            onSave()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getIntentData() {
        val user: User = intent.requireParcelableExtra(EXTRA_USER) as User
        viewModel.setUser(user)
    }

    // FIN NO TOCAR

    private fun setupViews() {
        setUserData()
        setListeners()
    }

    private fun setUserData() {
        with(binding) {
            userImg.loadUrl(currentUser.photoUrl)
            userEdtName.setText(currentUser.name)
            userEdtEmail.setText(currentUser.email)
            userEdtPhoneNumber.setText(currentUser.phoneNumber)
            userEdtAddress.setText(currentUser.address)
            userEdtWeb.setText(currentUser.web)
        }
        viewModel.setPhoto(currentUser.photoUrl)
    }


    private fun setListeners() {
        binding.userImg.setOnClickListener { viewModel.changePhoto() }
        binding.userEdtWeb.setOnEditorActionListener { _, _, _ ->
            onSave()
            true
        }
    }


    private fun onSave() {
        binding.run {
            val name = userEdtName.text.toString()
            val email = userEdtEmail.text.toString()
            val phone = userEdtPhoneNumber.text.toString()
            val address = userEdtAddress.text.toString()
            val web = userEdtWeb.text.toString()
            viewModel.updateUser(name, email, phone, address, web)
        }
    }


    private fun observeEvents() {
        viewModel.onEditUser.observeEvent(this) {
            if (it) finish()
        }

        viewModel.onShowSnackbar.observeEvent(this) { message ->
            binding.userEdtWeb.hideSoftKeyboard()
            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        }
    }


    companion object {
        private const val EXTRA_USER = "EXTRA_USER"

        fun newIntent(context: Context, user: User): Intent =
                Intent(context, EditUserActivity::class.java).putExtra(EXTRA_USER, user)
    }

}