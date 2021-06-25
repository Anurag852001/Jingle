package com.example.jingle.fragments


import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView


import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jingle.*



class home : Fragment(), SongClicked {

    private var listSongs= ArrayList<SongInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val thisContext=view.getContext()

        if(ContextCompat.checkSelfPermission(thisContext,android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
        {
            loadSongs()
        }
        else requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
        val searchView=view.findViewById<SearchView>(R.id.searchView)
        val recyclerView:RecyclerView=view.findViewById(R.id.songs_list)
        recyclerView.layoutManager= LinearLayoutManager(thisContext)
        val adapter:SongListAdapter= SongListAdapter(listSongs,this)
        recyclerView.adapter=adapter


        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {


                adapter.filter.filter(newText)
                return false

            }


        })

        return view
    }



    private fun loadSongs() {
        var uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        var selection: String = MediaStore.Audio.Media.IS_MUSIC + "!=0"
        var c = requireContext().contentResolver.query(uri, null, selection, null, null)
        if (c != null) {
            while (c.moveToNext()) {
                var url = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA))
                var author = c.getString((c.getColumnIndex(MediaStore.Audio.Media.ARTIST)))
                var title = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))

                var duration=c.getInt(c.getColumnIndex(MediaStore.Audio.Media.DURATION))
                listSongs.add(SongInfo(url, author, title,duration))
            }
        }



    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults[0]==PackageManager.PERMISSION_GRANTED && requestCode==1)
        {
            loadSongs()
        }

    }

    override fun onItemClicked(item: String,url:String,position: Int) {
        super.onItemClicked(item,url,position)
        val intent = Intent(requireContext(),song_viewer::class.java)

        intent.putExtra("list",listSongs)
        intent.putExtra("position",position)
        startActivity(intent)
    }

}