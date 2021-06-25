package com.example.jingle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class SongListAdapter( private var items:ArrayList<SongInfo>,private val listener:SongClicked) : RecyclerView.Adapter<SongList>() ,Filterable{
        var items2=items

   override fun getFilter():Filter{
       return object : Filter(){
           override fun performFiltering(constraint: CharSequence?): FilterResults {
               val const=constraint.toString()

               if(const.isNotEmpty()) {
                   val resultList=ArrayList<SongInfo>()
                   for(row in items){
                       if(row.title?.lowercase(Locale.ROOT)!!.contains(const.lowercase(Locale.ROOT)))
                           resultList.add(row)
                   }
                   resultList
               } else
                   items2=items
               val filterResults = FilterResults()
               filterResults.values = items2
               return filterResults
           }

           override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
               items2 = results?.values as ArrayList<SongInfo>
               notifyDataSetChanged()
           }

       }
   }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongList {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.songlist,parent,false)
        val viewHolder=SongList(view)
        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition].title.toString(),items[viewHolder.adapterPosition].url.toString(),viewHolder.adapterPosition)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: SongList, position: Int) {
        var currentItem=items2[position]

        holder.title.text=currentItem.title
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
class SongList(itemView: View): RecyclerView.ViewHolder(itemView){
    val title:TextView=itemView.findViewById(R.id.textView)

}
interface SongClicked{
    fun onItemClicked(item:String,url:String,position: Int){

    }

}
