package com.example.jingle.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jingle.R
import com.example.jingle.playlistAdapter


class Library : Fragment() {

    private val playlist= ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view= inflater.inflate(R.layout.fragment_library, container, false)



        val newPlaylistCreator=view.findViewById<View>(R.id.new_playlist_button)
        val textTaker=view.findViewById<EditText>(R.id.playlist_name)
        newPlaylistCreator.setOnClickListener{
            val builder=AlertDialog.Builder(requireContext())
            builder.setTitle("Enter The playlist Name")
            val dialogLayout = inflater.inflate(R.layout.playlist_specifier, null)
            val editText  = dialogLayout.findViewById<EditText>(R.id.editTextTextPersonName)
                builder.setView(dialogLayout)
            builder.setPositiveButton("Submit"){
                    dialogInterface, i ->
             val text=editText.text.toString()
                playlist.add(text)
            }
            builder.show()

        }

        val newPlaylistItem=view.findViewById<View>(R.id.playlist_item)
        val playlistRecyclerView= view.findViewById<RecyclerView>(R.id.playlistRecyclerView)
        playlistRecyclerView.layoutManager=LinearLayoutManager(requireContext())


        val adapter= playlistAdapter(playlist)
        playlistRecyclerView.adapter=adapter

        return view
    }


}