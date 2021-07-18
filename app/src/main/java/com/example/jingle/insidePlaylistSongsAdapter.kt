package com.example.jingle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView


class insidePlaylistSongsAdapter(private val items:ArrayList<SongInfo> ): RecyclerView.Adapter<Insideholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Insideholder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.inside_playlist_list,parent,false)
        return Insideholder(view)
    }

    override fun onBindViewHolder(holder: Insideholder, position: Int) {
       val curr=items[position]
        holder.songName.text=curr.title
        holder.artistName.text=curr.title

    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class Insideholder(itemView: View) :RecyclerView.ViewHolder(itemView){
    var songName: TextView =itemView.findViewById<TextView>(R.id.song_name_playlist)
    var artistName: TextView =itemView.findViewById<TextView>(R.id.artitist_name_playlist)


}
