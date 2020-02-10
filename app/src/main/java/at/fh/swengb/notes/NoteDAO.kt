package at.fh.swengb.notes

import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Query("DELETE FROM Note")
    fun deleteAllNotes()

    @Query("SELECT * FROM Note WHERE id = :id")
    fun findAll(id : String): Note

    @Query ("SELECT * FROM Note")
    fun allFromDb() : List<Note>

    /*@Query ("DELETE FROM Note WHERE id = :id")
    fun delFromDb (id : String)*/

}