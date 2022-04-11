package com.soulje.mapapp.db

import androidx.room.*

@Dao
interface MarkersDao {

    @Query("SELECT * FROM MarkerEntity")
    fun all(): MutableList<MarkerEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: MarkerEntity) : Long

    @Update
    fun update(entity: MarkerEntity)

    @Delete
    fun delete(entity: MarkerEntity)

}