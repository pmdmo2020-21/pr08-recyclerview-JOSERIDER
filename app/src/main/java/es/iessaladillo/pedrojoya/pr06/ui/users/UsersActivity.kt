package es.iessaladillo.pedrojoya.pr06.ui.users

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import es.iessaladillo.pedrojoya.pr06.R

class UsersActivity : AppCompatActivity() {

    // TODO: Código de la actividad.
    //  Ten en cuenta que el recyclerview se muestra en forma de grid,
    //  donde el número de columnas está gestionado
    //  por R.integer.users_grid_columns
    //  ...

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

    fun onAddUser() {
        // TODO: Acciones a realizar al querer agregar un usuario.
    }

}