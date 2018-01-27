package com.smassive.stararchwars.infrastructure

import android.app.Application
import android.content.Context
import android.os.StrictMode
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.android.autoAndroidModule
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.smassive.stararchwars.BuildConfig
import com.smassive.stararchwars.infrastructure.di.appModule

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

  fun addModule(activityModule: Kodein.Module) {
    kodein.addImport(activityModule, allowOverride = true)
  }
}

fun Context.asApp() = this.applicationContext as StarArchWarsApplication