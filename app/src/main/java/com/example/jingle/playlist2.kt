package com.example.jingle

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jingle.fragments.home
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.nio.file.Files.size

class playlist2 : AppCompatActivity(), PlaylistItemClicked {


    var flag = home.flag
    val topPosition = home.topPosition



    private val listSongs =splashScreen.listSongs

    companion object {

    var listSongs2 = ArrayList<ArrayList<SongInfo>>()
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist2)

        listSongs2=loadDataOne()

        var playlist=ArrayList<String>()
        playlist=loadData()
        val prevSize= playlist.size
        val sizeShower=findViewById<TextView>(R.id.sizeShower)
        sizeShower.text=listSongs2.size.toString()
        val newPlaylistCreator=findViewById<View>(R.id.new_playlist_button)
        val textTaker= findViewById<TextView>(R.id.playlist_name)
        val opener=findViewById<Button>(R.id.opener)
        opener.setOnClickListener{
            val intent=Intent(this,InsidePlaylistActivity::class.java)
            startActivity(intent)
        }
        newPlaylistCreator.setOnClickListener{

            createNewPlaylist(playlist)

        }



        val newPlaylistItem=findViewById<View>(R.id.playlist_item)
        val playlistRecyclerView= findViewById<RecyclerView>(R.id.playlistRecyclerView)
        val saveButton =findViewById<View>(R.id.saveChanges)
        playlistRecyclerView.layoutManager= LinearLayoutManager(this)


        saveButton.setOnClickListener{
            val builder= AlertDialog.Builder(this)
            val newSize= playlist.size
            if(prevSize==newSize)
            { builder.setTitle("No changes are made !")
                builder.setPositiveButton("OK"){
                        dialogInterface,i->

                }

            }

            else{
                builder.setTitle("Are you sure you want to save this")
                builder.setPositiveButton("save"){
                        dialogInterface, i -> saveData(playlist)
                    val newList=ArrayList<SongInfo>()
                    listSongs2.add(newList)
                    saveDataOne(listSongs2)
                    sizeShower.text= listSongs2.size.toString()
                    Toast.makeText(this,"All changes are saved",Toast.LENGTH_SHORT)

                }}
            builder.show()
        }


        val adapter= playlistAdapter(listSongs2,playlist,this )
        playlistRecyclerView.adapter=adapter

        val size= playlist.size









    }

    private fun createNewPlaylist(playlist: ArrayList<String>) {
        val builder= AlertDialog.Builder(this)



        val dialogLayout = LayoutInflater.from(this).inflate(R.layout.playlist_specifier, null)
        val editText  = dialogLayout.findViewById<EditText>(R.id.playlist_chooser)
        builder.setView(dialogLayout)
        builder.setPositiveButton("CREATE"){
                dialogInterface, i ->
            val text=editText.text.toString()
            playlist.add(text)

            saveData(playlist)
            val newList=ArrayList<SongInfo>()
            listSongs2.add(newList)
            saveDataOne(listSongs2)






        }
        builder.show()

    }


    private fun saveData(playlist: ArrayList<String>) {

        val sharedPreferences=this.getSharedPreferences("playlists", MODE_PRIVATE)
        val editor =sharedPreferences.edit()
        val gson= Gson()
        val json:String=gson.toJson(playlist)
        editor.putString("playlist",json)
        editor.apply()



    }
    private fun saveDataOne(playlist: ArrayList<ArrayList<SongInfo>>) {

        val sharedPreferences=this.getSharedPreferences("TwoD", MODE_PRIVATE)
        val editor =sharedPreferences.edit()
        val gson= Gson()
        val json:String=gson.toJson(playlist)
        editor.putString("TwoD",json)
        editor.apply()



    }
    private fun loadDataOne( ): ArrayList<ArrayList<SongInfo>> {
        var playlist= ArrayList<ArrayList<SongInfo>>()
        val sharedPreferences=this.getSharedPreferences("TwoD", MODE_PRIVATE)
        val gson= Gson()
        val json:String?=sharedPreferences.getString("TwoD", null)
        val type = object : TypeToken<ArrayList<ArrayList<SongInfo>>>() {}.type

        if(json==null)
        {
            Toast.makeText(this,"It is null ", Toast.LENGTH_SHORT)
        }
        playlist=gson.fromJson(json,type)

        return playlist



    }

    private fun loadData( ): ArrayList<String> {
        var playlist= ArrayList<String>()
        val sharedPreferences=this.getSharedPreferences("playlists", MODE_PRIVATE)
        val gson= Gson()
        val json:String?=sharedPreferences.getString("playlist", null)
        val type = object : TypeToken<ArrayList<String>>() {}.type

        if(json==null)
        {
            Toast.makeText(this,"It is null ", Toast.LENGTH_SHORT)
        }
        playlist=gson.fromJson(json,type)

        return playlist



    }
    private fun removeData(playlist: ArrayList<String>):ArrayList<String>
    {
        var newList=playlist
        newList.removeAt(3)
        return newList

    }

    override fun onItemClicked(position: Int) {

        if(flag==0)
        {
            val intent=Intent(this,InsidePlaylistActivity::class.java)
            intent.putExtra("playlistItemPosition",position)
            startActivity(intent)
        }
        else if(flag==1)
        {
            listSongs2[position].add(listSongs[topPosition])
            saveDataOne(listSongs2)
            val sizeShower=findViewById<TextView>(R.id.sizeShower)
            sizeShower.text=listSongs2.size.toString()
        }



    }


}