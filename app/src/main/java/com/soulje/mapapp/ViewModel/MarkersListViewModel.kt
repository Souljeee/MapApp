package com.soulje.mapapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.Marker
import com.soulje.mapapp.db.MarkerEntity
import com.soulje.mapapp.model.DataState
import com.soulje.mapapp.model.RoomRepository
import com.soulje.mapapp.model.RoomRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class MarkersListViewModel(
    private val liveDataForViewToObserve: MutableLiveData<DataState> = MutableLiveData()
) : ViewModel() {

    private val repositoryImpl : RoomRepository by inject(RoomRepository::class.java)

    fun getData(): LiveData<DataState>{
        getMarkersData()
        return liveDataForViewToObserve
    }

    private fun getMarkersData() {
        viewModelScope.launch {
            async (Dispatchers.IO){
                val response = repositoryImpl.getAllMarkers()
                liveDataForViewToObserve.postValue(DataState.Success(response))
            }
        }
    }

    fun updateMarker(marker: MarkerEntity){
        viewModelScope.launch {
            async(Dispatchers.IO){
                repositoryImpl.updateMarker(marker)
            }
        }
    }

    fun deleteMarker(marker: MarkerEntity){
        viewModelScope.launch {
            async(Dispatchers.IO){
                repositoryImpl.deleteMarker(marker)
            }
        }
    }



}