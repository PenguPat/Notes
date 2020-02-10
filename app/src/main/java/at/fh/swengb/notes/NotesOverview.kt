package at.fh.swengb.notes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_notes_overview.*
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class NotesOverview : AppCompatActivity() {
    val notesAdapter = NotesAdapter(){
        val intent = Intent(this, NoteDetail::class.java)
        intent.putExtra(EXTRA_NOTE_ID, it.id)
        startActivityForResult(intent, ADD_OR_EDIT_NOTE_REQUEST)
    }
    companion object {
        val EXTRA_NOTE_ID = "NOTE_ID_EXTRA"
        val ADD_OR_EDIT_NOTE_REQUEST = 1
    }

    override fun onActivityResult(requestCode:Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_OR_EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {

        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_overview)


        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)


        NotesRepository.noteList(
            token = sharedPreferences.getString("accessToken","").toString(),
            sync = sharedPreferences.getLong("LastSync",0),
            success = {
                it.notes.map{NotesRepository.insert(this, it)}
                notesAdapter.updateList(NotesRepository.allFromDb(this))
            },
            error = {
                Log.e("error", it)
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            },
            context = this

        )

        note_recycler_view.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        note_recycler_view.adapter = notesAdapter

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    inline fun consume(f: () -> Unit): Boolean {
        f()
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.logout -> consume { Toast.makeText(this, getString(R.string.Logout), Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)

                NotesRepository.deleteAllNotes(this)

                sharedPreferences.edit().remove("accessToken").apply()
                sharedPreferences.edit().remove("lastSync").apply()

                startActivity(intent)
            }

            R.id.news -> consume { Toast.makeText(this, getString(R.string.New), Toast.LENGTH_SHORT).show()
                val intent = Intent(this, NoteDetail::class.java)
                startActivity(intent)
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
