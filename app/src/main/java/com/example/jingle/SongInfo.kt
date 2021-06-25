package com.example.jingle

import android.os.Parcel
import android.os.Parcelable


class SongInfo() :Parcelable  {
    var title:String ?=null
    var url:String ?=null
    var author:String ?=null
    var duration:Int ?=null

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        url = parcel.readString()
        author = parcel.readString()
        duration = parcel.readValue(Int::class.java.classLoader) as? Int
    }


    constructor( url: String ?,  author: String?, title: String?, duration: Int?  ) : this() {
        this.title=title
        this.author=author
        this.url=url
        this.duration=duration

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(author)
        parcel.writeValue(duration)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SongInfo> {
        override fun createFromParcel(parcel: Parcel): SongInfo {
            return SongInfo(parcel)
        }

        override fun newArray(size: Int): Array<SongInfo?> {
            return arrayOfNulls(size)
        }
    }
}
