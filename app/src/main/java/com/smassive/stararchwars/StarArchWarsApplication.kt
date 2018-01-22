package com.smassive.stararchwars

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.os.StrictMode
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.github.salomonbrys.kodein.singleton
import com.smassive.stararchwars.data.films.datasource.local.FilmDao
import com.smassive.stararchwars.data.films.datasource.local.FilmsRoomSource
import com.smassive.stararchwars.data.films.datasource.remote.FilmsFirebaseSource
import com.smassive.stararchwars.data.films.repository.FilmsRepository

class StarArchWarsApplication : Application(), KodeinAware {

  override val kodein by Kodein.lazy {
    bind<FilmsRoomSource>() with singleton {
      Room.databaseBuilder(asApp(), FilmsRoomSource::class.java, FilmsRoomSource.DB_NAME).build()
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

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
          .detectDiskReads()
          .detectDiskWrites()
          .detectNetwork()   // or .detectAll() for all detectable problems
          .penaltyLog()
          .build())
      StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
          .detectLeakedSqlLiteObjects()
          .detectLeakedClosableObjects()
          .penaltyLog()
          .penaltyDeath()
          .build())
    }
  }
}

fun Context.asApp() = this.applicationContext as StarArchWarsApplication