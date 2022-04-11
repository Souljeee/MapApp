package com.soulje.mapapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = arrayOf(MarkerEntity::class),
    version = 2,
    exportSchema = false
)
abstract class MarkersDataBase : RoomDatabase() {
    abstract fun markersDao(): MarkersDao
}