package com.example.jingle

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jingle.fragments.Library

class playlistAdapter (private var items:ArrayList<String>,private val listener:playlistItemClicked  ): RecyclerView.Adapter<playlist>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): playlist {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.playlist_list, parent, false)
        return playlist(view)
    }

    override fun onBindViewHolder(holder: playlist, position: Int) {
     var currentItem=items[position]
        holder.playlistName.text=currentItem
        holder.deleteButton.setOnClickListener{
            removeAt(position)


        }


    }


    override fun getItemCount(): Int {
        return items.size
    }
    fun removeAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }

}

interface playlistItemClicked {
    fun onItemClicked(){

    }
}


class playlist(itemView: View): RecyclerView.ViewHolder(itemView) {
    var playlistName=itemView.findViewById<TextView>(R.id.playlist_name)
    val deleteButton=itemView.findViewById<Button>(R.id.deleteItem)


}


