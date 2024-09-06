package com.example.newsapp.Room

import androidx.room.TypeConverter
import com.example.newsapp.module.Source

class TypeConverter {
    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }
    @TypeConverter
    fun toSource(name:String):Source{
        return Source(name,name)
    }
}