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
import es.iessaladillo.pedrojoya.pr06.utils.*

class AddUserActivity : AppCompatActivity() {


    private val binding: UserActivityBinding by lazy {
        UserActivityBinding.inflate(layoutInflater)
    }
    private val viewModel: AddUserViewModel by viewModels {
        AddUserViewModelFactory(application, Database, this)
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
            binding.userEdtWeb.hideSoftKeyboard()
            Snackbar.make(binding.clRoot, it, Snackbar.LENGTH_LONG).show()
        }
    }


    private fun onSave() {
        binding.run {
            val name = userEdtName.text.toString()
            val email = userEdtEmail.text.toString()
            val phone = userEdtPhoneNumber.text.toString()
            val address = userEdtAddress.text.toString()
            val web = userEdtWeb.text.toString()
            viewModel.insertUser(name, email, phone, address, web)
        }
    }


    private fun changePhoto() = viewModel.changePhoto()


    companion object {
        fun newIntent(context: Context): Intent = Intent(context, AddUserActivity::class.java)
    }
}