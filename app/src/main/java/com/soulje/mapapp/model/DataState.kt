package com.soulje.mapapp.model

import com.google.android.gms.maps.model.MarkerOptions

sealed class DataState{
    data class Success(val markersData: MutableList<MarkerOptions>) : DataState()
    data class Error(val error : Throwable) : DataState()
    data class Loading(val progress: Int?) : DataState()
}
