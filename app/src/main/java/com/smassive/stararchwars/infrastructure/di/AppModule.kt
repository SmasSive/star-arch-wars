package com.smassive.stararchwars.infrastructure.di

import android.arch.persistence.room.Room
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.smassive.stararchwars.data.films.datasource.local.FilmDao
import com.smassive.stararchwars.data.films.datasource.local.FilmsRoomSource
import com.smassive.stararchwars.data.films.datasource.remote.FilmsFirebaseSource
import com.smassive.stararchwars.data.films.repository.FilmsRepository
import com.smassive.stararchwars.infrastructure.StarArchWarsApplication

fun appModule(starArchWarsApplication: StarArchWarsApplication) = Kodein.Module {
  bind<FilmsRoomSource>() with singleton {
    Room.databaseBuilder(starArchWarsApplication, FilmsRoomSource::class.java, FilmsRoomSource.DB_NAME).build()
  }

  bind<FilmDao>() with singleton {
    instance<FilmsRoomSource>().filmDao()
  }

  bind<FilmsFirebaseSource>() with singleton {
    FilmsFirebaseSource()
  }

  bind<FilmsRepository>() with singleton {
    FilmsRepository(instance(), instance())
  }
}