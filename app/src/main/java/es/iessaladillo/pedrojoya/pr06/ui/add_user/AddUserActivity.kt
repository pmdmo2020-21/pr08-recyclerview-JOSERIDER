package es.iessaladillo.pedrojoya.pr06.ui.add_user

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.pr06.R

class AddUserActivity : AppCompatActivity() {

    // TODO: Código de la actividad.
    //  ...

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

    private fun onSave() {
        // TODO: Acciones a realizar al querer salvar
    }

}