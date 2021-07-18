package com.example.jingle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RecoverySystem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mtechviral.mplaylib.MusicFinder

class InsidePlaylistActivity : AppCompatActivity() {

    private val insidePlaylistSongs2 = playlist2.listSongs2
    private var position=intent.getIntExtra("playlistItemPosition",0)

    private var insidePlaylistSongs=insidePlaylistSongs2[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inside_playlist)


        for (i in insidePlaylistSongs2)
        {
            if(insidePlaylistSongs2.size!=0)
                insidePlaylistSongs=i
            break
        }

       val recyclerView=findViewById<RecyclerView>(R.id.iv_insidePlayListRecyclerView)
        val adapter=insidePlaylistSongsAdapter(insidePlaylistSongs)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=adapter
    }
}