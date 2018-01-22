package com.smassive.stararchwars

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.os.StrictMode
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.android.autoAndroidModule
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.github.salomonbrys.kodein.scopedSingleton
import com.github.salomonbrys.kodein.singleton
import com.smassive.stararchwars.data.films.datasource.local.FilmDao
import com.smassive.stararchwars.data.films.datasource.local.FilmsRoomSource
import com.smassive.stararchwars.data.films.datasource.remote.FilmsFirebaseSource
import com.smassive.stararchwars.data.films.repository.FilmsRepository
import com.smassive.stararchwars.films.view.FilmsAdapter

class StarArchWarsApplication : Application(), KodeinAware {

  override val kodein by Kodein.lazy {
    import(autoAndroidModule(asApp()))

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

    bind<FilmsAdapter>() with scopedSingleton(androidActivityScope) {
      FilmsAdapter()
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