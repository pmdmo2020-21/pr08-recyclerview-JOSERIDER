package es.iessaladillo.pedrojoya.pr06.ui.add_user

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
import es.iessaladillo.pedrojoya.pr06.utils.hideSoftKeyboard
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl
import es.iessaladillo.pedrojoya.pr06.utils.observeEvent

class AddUserActivity : AppCompatActivity() {


    private val binding: UserActivityBinding by lazy {
        UserActivityBinding.inflate(layoutInflater)
    }
    private val viewModel: AddUserViewModel by viewModels {
        AddUserViewModelFactory(Database, this)
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

    // FIN NO TOCAR


    private fun observeLiveData() {
        viewModel.photoUrl.observe(this, Observer { url ->
            binding.userImg.loadUrl(url)
        })
    }

    private fun setupViews() {
        setListeners()
    }

    private fun setListeners() {
        binding.userImg.setOnClickListener { changePhoto() }
        binding.userEdtWeb.setOnEditorActionListener { _, _, _ ->
            onSave()
            true
         }
    }

    private fun observeEvents() {
        viewModel.onSaveUser.observeEvent(this) { if (it) finish() }
        viewModel.onShowSnackbar.observeEvent(this) {
            Snackbar.make(binding.clRoot, it, Snackbar.LENGTH_LONG).show()
        }
    }


    private fun onSave() {
        if (isValidForm()) {
            binding.run {
                val name = userEdtName.text.toString()
                val email = userEdtEmail.text.toString()
                val phone = userEdtPhoneNumber.text.toString()
                val address = userEdtAddress.text.toString()
                val web = userEdtWeb.text.toString()
                val user = User(0, name, email, phone, address, web, viewModel.photoUrl.value!!)
                viewModel.insertUser(user)
            }
        } else {
            viewModel.showSnackbar(getString(R.string.user_invalid_data))
            binding.userEdtWeb.hideSoftKeyboard()
        }
    }


    private fun isValidForm(): Boolean =
           isValidName() && isValidEmail() && isValidPhoneNumber()


    private fun isValidPhoneNumber(): Boolean {
        val phone = binding.userEdtPhoneNumber.text.toString()
        return phone.startsWith("6") && phone.length == 9
    }

    private fun isValidEmail(): Boolean {
        val email = binding.userEdtEmail.text.toString()
        val regex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex()
        return regex matches email
    }

    private fun isValidName(): Boolean {
        val name = binding.userEdtName.text.toString()
        return name.isNotBlank() || name.isNotEmpty()
    }

    private fun changePhoto() = viewModel.changePhoto()


    companion object {
        fun newIntent(context: Context): Intent = Intent(context, AddUserActivity::class.java)
    }
}