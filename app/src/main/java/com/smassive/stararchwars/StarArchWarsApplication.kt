package com.smassive.stararchwars

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.os.StrictMode
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.android.autoAndroidModule
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.smassive.stararchwars.data.films.datasource.local.FilmDao
import com.smassive.stararchwars.data.films.datasource.local.FilmsRoomSource
import com.smassive.stararchwars.data.films.datasource.remote.FilmsFirebaseSource
import com.smassive.stararchwars.data.films.repository.FilmsRepository

class StarArchWarsApplication : Application(), KodeinAware {

  override val kodein = ConfigurableKodein(mutable = true)

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

    initializeInjector()
  }

  private fun initializeInjector() {
    kodein.clear()
    kodein.addImport(autoAndroidModule(asApp()))
    kodein.addImport(appModule(asApp()))
  }

  private fun appModule(context: Context) = Kodein.Module {
    bind<FilmsRoomSource>() with singleton {
      Room.databaseBuilder(context, FilmsRoomSource::class.java, FilmsRoomSource.DB_NAME).build()
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

  fun addModule(activityModule: Kodein.Module) {
    kodein.addImport(activityModule)
  }
}

fun Context.asApp() = this.applicationContext as StarArchWarsApplication