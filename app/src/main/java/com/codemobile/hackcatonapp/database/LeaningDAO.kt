package com.codemobile.hackcatonapp.database

import androidx.room.*
import com.codemobile.hackcatonapp.model.LeandingModel

@Dao
interface LeaningDAO {
    @Query(value = "select * from lending")
    fun queryFavorites(): List<DatabaseEntity>

    @Insert
    fun addFavorite(favoriteEntity: DatabaseEntity)

    @Update
    fun updateFavorite(favoriteEntity: DatabaseEntity)

    @Delete
    fun deleteFavorite(favoriteEntity: DatabaseEntity)
}