package com.example.jingle

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.view.*
import android.widget.*
import androidx.appcompat.view.menu.MenuAdapter

import androidx.recyclerview.widget.RecyclerView
import com.example.jingle.fragments.home
import java.io.ByteArrayInputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.measureNanoTime

class SongListAdapter( private var items:ArrayList<SongInfo>,private val listener:SongClicked,private val Listener2: onMenuItemClicked) : RecyclerView.Adapter<SongList>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongList {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.songlist,parent,false)
        val viewHolder=SongList(view)
        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition].title.toString(),items[viewHolder.adapterPosition].url.toString(),viewHolder.adapterPosition)
        }
        viewHolder.menubutton.setOnClickListener{
            Listener2.onMenuItemClicked(viewHolder.adapterPosition,viewHolder.menubutton)

        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: SongList, position: Int) {
        var currentItem=items[position]
        val mmr=MediaMetadataRetriever()
        holder.title.text=currentItem.title.toString()
        loadImage(mmr,position,holder.art,currentItem)
        holder.author.text=currentItem.author


    }

    override fun getItemCount(): Int {
        return items.size
    }

}

interface onMenuItemClicked {
    fun onMenuItemClicked(position: Int,itemView: View)
}

class SongList(itemView: View): RecyclerView.ViewHolder(itemView){
    val title:TextView=itemView.findViewById(R.id.TextView)
    val art=itemView.findViewById<ImageView>(R.id.imageView)
    val author=itemView.findViewById<TextView>(R.id.artist_name)
     val menubutton=itemView.findViewById<ImageView>(R.id.song_menu)


}
interface SongClicked{
    fun onItemClicked(item:String,url:String,position: Int){

    }


}

private fun loadImage(mmr: MediaMetadataRetriever, position:Int, songImage:ImageView,curr:SongInfo)
{
    mmr.setDataSource(curr.url)
    val artBytes: ByteArray? = mmr.embeddedPicture
    if (artBytes != null) {
        val image = ByteArrayInputStream(mmr.embeddedPicture)
        val bm = BitmapFactory.decodeStream(image)
        songImage.setImageBitmap(bm)
    } else {
        songImage.setImageResource(R.drawable.default_mini_cover)
    }
}