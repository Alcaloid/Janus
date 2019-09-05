package com.codemobile.hackcatonapp.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "lending")
data class DatabaseEntity(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @NonNull val limit:Int,
    @NonNull val peroid:String,
    val interrest:Int,
    val status:String

)