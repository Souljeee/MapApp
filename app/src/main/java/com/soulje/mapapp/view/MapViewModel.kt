package com.soulje.mapapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.soulje.mapapp.db.MarkerEntity
import com.soulje.mapapp.model.DataState
import com.soulje.mapapp.model.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class MapViewModel(
    private val liveDataForViewToObserve: MutableLiveData<DataState> = MutableLiveData()
) : ViewModel() {

    private val repositoryImpl : RoomRepository by KoinJavaComponent.inject(RoomRepository::class.java)

    fun getData(): LiveData<DataState> {
        getAllMarkers()
        return liveDataForViewToObserve
    }

    fun addMarker(marker : MarkerEntity){
        viewModelScope.launch {
            async (Dispatchers.IO){ marker.id = repositoryImpl.addMarker(marker) }
        }
    }

    fun getAllMarkers(){
        liveDataForViewToObserve.value = DataState.Loading(0)
        viewModelScope.launch {
            async(Dispatchers.IO) {
                liveDataForViewToObserve.postValue(
                    DataState.Success(repositoryImpl.getAllMarkers())
                )
            }
        }
    }
}