package com.soulje.mapapp

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.soulje.mapapp.db.MarkerEntity

fun Marker.toEntity(): MarkerEntity{
    return MarkerEntity(
        id = 0,
        title = this.title!!,
        snippet = this.snippet,
        lat = this.position.latitude,
        lng = this.position.longitude
    )
}

fun MarkerEntity.toMarkerOptions() : MarkerOptions {
    return MarkerOptions()
        .title(this.title)
        .snippet(this.snippet)
        .position(
            LatLng(this.lat, this.lng)
        )
}

fun MarkerOptions.toEntity() : MarkerEntity {
    return MarkerEntity(
        id = 0,
        title = this.title!!,
        snippet = this.snippet!!,
        lat = this.position.latitude,
        lng = this.position.longitude
    )
}

