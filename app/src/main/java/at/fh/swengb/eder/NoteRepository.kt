package at.fh.swengb.eder

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NoteRepository {

    fun getNotes(token: String, lastSync: Long, success:(noteResponse: NotesResponse)->Unit,error:(errorMessage:String)->Unit){
        NoteApi.retrofitService.notes(token, lastSync).enqueue(object : Callback<NotesResponse>{

            override fun onFailure(call: Call<NotesResponse>, t: Throwable) {
                error("Call failed")
            }

            override fun onResponse(call: Call<NotesResponse>, response: Response<NotesResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    success(responseBody)
                }
                else {
                    error("Error")

                }
            }
        })
    }

    fun uploadNote (token: String, note2Upload: Note, success: (note: Note)->Unit, error: (errorMessage: String)->Unit){
        NoteApi.retrofitService.addOrUpdateNote(token, note2Upload).enqueue(object : Callback<Note>{

            override fun onFailure(call: Call<Note>, t: Throwable) {
                error("Call failed " + t.localizedMessage)
            }

            override fun onResponse(call: Call<Note>, response: Response<Note>) {
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    success(responseBody)
                }

                else {
                    error("Error " + response.message())
                }
            }
        })
    }

    fun addNote(context: Context, newNote: Note) {
        val db = NoteDatabase.getDatabase(context)
        db.NoteDao.insert(newNote)
    }

    fun getNoteById (context: Context, id: String):Note {
        val db = NoteDatabase.getDatabase(context)
        return db.NoteDao.findNoteById(id)
    }

    fun getNotesAll (context: Context):List<Note> {
        val db = NoteDatabase.getDatabase(context)
        return db.NoteDao.getNotesAll()
    }

    fun clearDb (context: Context) {
        val db = NoteDatabase.getDatabase(context)
        return db.NoteDao.deleteAllNote()
    }
}