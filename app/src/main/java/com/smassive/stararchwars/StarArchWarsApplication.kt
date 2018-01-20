package com.smassive.stararchwars

import android.app.Application
import android.arch.persistence.room.Room
import android.os.StrictMode
import com.smassive.stararchwars.data.films.datasource.local.FilmsRoomSource

class StarArchWarsApplication : Application() {

  lateinit var roomDb: FilmsRoomSource

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

    roomDb = Room.databaseBuilder(this, FilmsRoomSource::class.java, FilmsRoomSource.DB_NAME).build()
  }
}