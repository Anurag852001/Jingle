package com.example.jingle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongListAdapter( private val items:ArrayList<SongInfo>,private val listener:SongClicked) : RecyclerView.Adapter<SongList>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongList {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.songlist,parent,false)
        val viewHolder=SongList(view)
        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition].title.toString(),items[viewHolder.adapterPosition].url.toString(),viewHolder.adapterPosition)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: SongList, position: Int) {
    val currentItem=items[position]
        holder.title.text=currentItem.title
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
class SongList(itemView: View): RecyclerView.ViewHolder(itemView){
    val title:TextView=itemView.findViewById(R.id.textView)

}
interface SongClicked{
    fun onItemClicked(item:String,url:String,position: Int){

    }
}