package at.fh.swengb.notes

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NotesRepository {

    fun login(success: (logintoken: AuthResponse) -> Unit, error: (errorMessage: String) -> Unit, request: AuthRequest, context: Context)
    {
        NotesApi.retrofitService.login(request).enqueue(object : Callback<AuthResponse> {
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                error(context.getString(R.string.CallFail))
            }

            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    success(responseBody)
                } else {
                    error(context.getString(R.string.WrongUP))
                }
            }

        })
    }
    fun noteList(success: (noteList: NotesResponse) -> Unit, error: (errorMessage: String) -> Unit, token : String, sync : Long, context: Context)
    {
        NotesApi.retrofitService.notes(token, sync).enqueue(object : Callback<NotesResponse> {
            override fun onFailure(call: Call<NotesResponse>, t: Throwable) {
                error(context.getString(R.string.CallFail))
            }

            override fun onResponse(
                call: Call<NotesResponse>,
                response: Response<NotesResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    success(responseBody)
                } else {
                    error(context.getString(R.string.Error))
                }
            }

        })
    }
    fun addOrUpdateNote(
        token: String,
        body: Note,
        success: (movieList: Note) -> Unit,
        error: (errorMessage: String) -> Unit,
        context: Context
    ) {
        NotesApi.retrofitService.addOrUpdateNote(token, body).enqueue(object : Callback<Note> {
            override fun onFailure(call: Call<Note>, t: Throwable) {
                error(context.getString(R.string.CallFail))
            }

            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    success(responseBody)
                } else {
                    error(context.getString(R.string.Error))
                }
            }

        })
    }

    fun deleteAllNotes(context: Context) {
        var db = NoteDatabase.getDatabase(context.applicationContext)
        db.NoteDao.deleteAllNotes()
    }

    fun insert(context: Context, note: Note) {
        var db = NoteDatabase.getDatabase(context.applicationContext)
        db.NoteDao.insert(note)
    }

    fun update(context: Context, note: Note) {
        var db = NoteDatabase.getDatabase(context.applicationContext)
        db.NoteDao.update(note)
    }

    fun findAll(context: Context, id: String) : Note {
        var db = NoteDatabase.getDatabase(context.applicationContext)
        return db.NoteDao.findAll(id)
    }

    fun allFromDb(context : Context) : List<Note> {
        var db = NoteDatabase.getDatabase(context.applicationContext)
        return db.NoteDao.allFromDb()
    }

    /*fun deleteNote(context: Context, id: String) {
        var db = NoteDatabase.getDatabase(context.applicationContext)
        db.NoteDao.delFromDb(id)
    }*/
}

