package com.soulje.mapapp.model

import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.soulje.mapapp.db.MarkerEntity
import com.soulje.mapapp.db.MarkersDao
import com.soulje.mapapp.toEntity
import com.soulje.mapapp.toMarkerOptions
import org.koin.java.KoinJavaComponent.inject

class RoomRepositoryImpl : RoomRepository {

    val markersDao: MarkersDao by inject(MarkersDao::class.java)

    override fun addMarker(marker: MarkerEntity): Long {
        return markersDao.insert(marker)
    }

    override fun deleteMarker(marker: MarkerEntity) {
        markersDao.delete(marker)
    }

    override fun getAllMarkers(): MutableList<MarkerOptions> {
        return markersDao.all().map { it.toMarkerOptions() }.toMutableList()
    }

    override fun updateMarker(marker: MarkerEntity) {
        markersDao.update(marker)
    }

}