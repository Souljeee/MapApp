package com.soulje.mapapp.di

import androidx.room.Room
import com.soulje.mapapp.ViewModel.MarkersListViewModel
import com.soulje.mapapp.db.MarkersDao
import com.soulje.mapapp.db.MarkersDataBase
import com.soulje.mapapp.model.RoomRepository
import com.soulje.mapapp.model.RoomRepositoryImpl
import com.soulje.mapapp.view.MapViewModel
import com.soulje.mapapp.view.MarkersListAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<MarkersDataBase> { Room.databaseBuilder(androidContext(), MarkersDataBase::class.java, "database.db").fallbackToDestructiveMigration().build()}

    single<MarkersDao> { get<MarkersDataBase>().markersDao() }

    viewModel{ MarkersListViewModel() }

    viewModel{ MapViewModel() }

    single<RoomRepository> { RoomRepositoryImpl() }


}