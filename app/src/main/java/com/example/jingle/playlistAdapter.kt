package com.example.jingle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class playlistAdapter (private val items:ArrayList<String>  ): RecyclerView.Adapter<playlist>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): playlist {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.playlist_list, parent, false)
        return playlist(view)
    }

    override fun onBindViewHolder(holder: playlist, position: Int) {
     var currentItem=items[position]
        holder.playlistName.text=currentItem
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class playlist(itemView: View): RecyclerView.ViewHolder(itemView) {
    var playlistName=itemView.findViewById<TextView>(R.id.playlist_name)


}
