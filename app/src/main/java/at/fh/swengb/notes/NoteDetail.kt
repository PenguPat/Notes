package at.fh.swengb.notes

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_note_detail.*
import kotlinx.android.synthetic.main.item_note.view.*
import java.util.*

class NoteDetail : AppCompatActivity() {

    companion object {
        val EXTRA_NOTE_ID = "NOTE_ID_EXTRA"
        //val ADD_OR_EDIT_NOTE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        if (intent.hasExtra(NotesOverview.EXTRA_NOTE_ID)) {

            val noteId = intent.getStringExtra(NotesOverview.EXTRA_NOTE_ID)


            val note: Note = NotesRepository.findAll(this, noteId)


            detail_note_title.setText(note.title)
            detail_note_content.setText(note.text)
        }

    }
    fun overwriteOverview(note : Note) {



        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)


            NotesRepository.addOrUpdateNote(
                token = sharedPreferences.getString("accessToken", "").toString(),
                body = note,
                success = {

                    NotesRepository.update(this, it)

                },
                error = {
                    Log.e("error", it)
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                },
                context = this
            )





    }

    override fun onCreateOptionsMenu(menudetail: Menu?): Boolean {
        menuInflater.inflate(R.menu.menudetail, menudetail)
        return true
    }
    inline fun consume(f: () -> Unit): Boolean {
        f()
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {

                R.id.save -> consume {
                    if (detail_note_content.text.isNullOrEmpty() && detail_note_title.text.isNullOrEmpty()) {

                        Toast.makeText(this, getString(R.string.Empty), Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(this, getString(R.string.Saved), Toast.LENGTH_SHORT).show()


                        val noteId = intent.getStringExtra(EXTRA_NOTE_ID)
                        val uuidString = UUID.randomUUID().toString()
                        var noteTitle: String = detail_note_title.text.toString()
                        var noteText: String = detail_note_content.text.toString()

                        if (noteId == null) {
                            overwriteOverview(Note(uuidString, noteTitle, noteText, true))
                        } else {
                            overwriteOverview(Note(noteId, noteTitle, noteText, true))
                        }
                        val intent = Intent(this, NotesOverview::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

            /*R.id.delete -> consume {

                val noteId = intent.getStringExtra(NotesOverview.EXTRA_NOTE_ID)


                NotesRepository.deleteNote(this, noteId)

                val intent = Intent(this, NotesOverview::class.java)
                startActivity(intent)
                finish()
            }*/


            else -> super.onOptionsItemSelected(item)
        }
    }
}
