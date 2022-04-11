package com.soulje.mapapp.model

import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.soulje.mapapp.db.MarkerEntity

interface RoomRepository {

    fun addMarker(marker: MarkerEntity): Long

    fun deleteMarker(marker: MarkerEntity)

    fun getAllMarkers() : MutableList<MarkerOptions>

    fun updateMarker(marker: MarkerEntity)

}