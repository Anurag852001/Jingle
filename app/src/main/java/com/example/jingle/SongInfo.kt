package com.example.jingle

import android.graphics.Bitmap
import android.media.Image


class SongInfo()   {
    var title:String ?=null
    var url:String ?=null
    var author:String ?=null
    var duration:Int ?=null





    constructor(url: String ?, author: String?, title: String?, duration: Int?) : this() {
        this.title=title
        this.author=author
        this.url=url
        this.duration=duration


    }

}
