package com.codemobile.mobilephonebuyersguide.database

import androidx.room.*
import com.codemobile.hackcatonapp.model.LeandingModel

@Dao
interface FavoriteDAO {
    @Query(value = "select * from favorite")
    fun queryFavorites(): List<LeandingModel>

    @Insert
    fun addFavorite(favoriteEntity: LeandingModel)

    @Update
    fun updateFavorite(favoriteEntity: LeandingModel)

    @Delete
    fun deleteFavorite(favoriteEntity: LeandingModel)
}