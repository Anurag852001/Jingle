package com.example.jingle.fragments

import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jingle.*
import com.google.gson.Gson
import java.lang.reflect.Type
import com.google.gson.reflect.TypeToken as TypeToken


class Library : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

       var playlist=loadData()
        val view= inflater.inflate(R.layout.fragment_library, container, false)


        val prevSize=playlist.size

        val newPlaylistCreator=view.findViewById<View>(R.id.new_playlist_button)
        val textTaker=view.findViewById<EditText>(R.id.playlist_name)


        newPlaylistCreator.setOnClickListener{
            val builder=AlertDialog.Builder(requireContext())


            val dialogLayout = inflater.inflate(R.layout.playlist_specifier, null)
            val editText  = dialogLayout.findViewById<EditText>(R.id.playlist_chooser)
                builder.setView(dialogLayout)
            builder.setPositiveButton("CREATE"){
                    dialogInterface, i ->
             val text=editText.text.toString()
                playlist.add(text)
                saveData(playlist)



            }
            builder.show()


        }


        val newPlaylistItem=view.findViewById<View>(R.id.playlist_item)
        val playlistRecyclerView= view.findViewById<RecyclerView>(R.id.playlistRecyclerView)
        val saveButton =view.findViewById<View>(R.id.saveChanges)
        playlistRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        saveButton.setOnClickListener{
         val builder= AlertDialog.Builder(requireContext())
            val newSize=playlist.size
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
                Toast.makeText(requireContext(),"All changes are saved",Toast.LENGTH_SHORT)

            }}
            builder.show()
        }


        val adapter= playlistAdapter(playlist)
        playlistRecyclerView.adapter=adapter

        val size=playlist.size
        
        return view
    }

    private fun saveData(playlist: ArrayList<String>) {

        val sharedPreferences=requireContext().getSharedPreferences("playlists", MODE_PRIVATE)
        val editor =sharedPreferences.edit()
        val gson= Gson()
        val json:String=gson.toJson(playlist)
        editor.putString("playlist",json)
        editor.apply()



    }
private fun loadData( ): ArrayList<String> {
    var playlist= ArrayList<String>()
    val sharedPreferences=requireContext().getSharedPreferences("playlists", MODE_PRIVATE)
    val gson=Gson()
    val json:String?=sharedPreferences.getString("playlist", null)
    val type = object : TypeToken<ArrayList<String>>() {}.type

    if(json==null)
    {
        Toast.makeText(requireContext(),"It is null ",Toast.LENGTH_SHORT)
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




}