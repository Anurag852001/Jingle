package com.example.jingle.fragments


import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jingle.*
import java.util.*
import kotlin.collections.ArrayList


class home : Fragment(), SongClicked {

    private var listSongs= ArrayList<SongInfo>()
    private var displaylist=ArrayList<SongInfo>()

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


        val searchPlateId = searchView.context.resources.getIdentifier("android:id/search_plate", null, null)
        val v=searchView.findViewById<View>(searchPlateId)
        v.setBackgroundColor(Color.TRANSPARENT)
        val recyclerView:RecyclerView=view.findViewById(R.id.songs_list)
        displaylist.addAll(listSongs)
        recyclerView.layoutManager= LinearLayoutManager(thisContext)
        val adapter:SongListAdapter= SongListAdapter(displaylist,this)
        recyclerView.adapter=adapter



        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText!!.isNotEmpty()) {
                    displaylist.clear()
                    val search = newText.toLowerCase(Locale.getDefault())

                    listSongs.forEach {
                        if (it.title!!.toLowerCase(Locale.getDefault()).contains(search)) {
                            displaylist.add(it)
                            recyclerView.adapter!!.notifyDataSetChanged()
                        }
                    }
                    recyclerView.adapter!!.notifyDataSetChanged()

                }
                else {

                            displaylist.clear()
                            displaylist.addAll(listSongs)
                            recyclerView.adapter!!.notifyDataSetChanged()
                        }

                    return true
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


        intent.putExtra("position",position)
        startActivity(intent)
    }

}