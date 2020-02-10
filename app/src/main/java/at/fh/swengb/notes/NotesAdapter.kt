package at.fh.swengb.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_note.view.*

class NotesAdapter(val clickListener: (note: Note) -> Unit): RecyclerView.Adapter<NotesViewHolder>() {

    private var noteList = listOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): NotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val noteItemView = inflater.inflate(R.layout.item_note, parent, false)
        return NotesViewHolder(noteItemView, clickListener)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        var note = noteList[position]
        holder.bindItem(note)
    }
    fun updateList(newList: List<Note>) {
        noteList = newList
        notifyDataSetChanged()
    }
}

class NotesViewHolder(itemView: View, val clickListener: (note: Note) -> Unit): RecyclerView.ViewHolder(itemView) {
    fun bindItem(note: Note) {

        itemView.item_note_title.text = note.title
        itemView.item_note_text.text = note.text

        itemView.setOnClickListener {
            clickListener(note)
        }
    }

}