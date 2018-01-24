package com.smassive.stararchwars.base.view

import android.os.Bundle
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.smassive.stararchwars.asApp

abstract class BaseActivity : KodeinAppCompatActivity() {

  abstract val activityModule: Kodein.Module

  override fun onCreate(savedInstanceState: Bundle?) {
    applicationContext.asApp().addModule(activityModule)
    super.onCreate(savedInstanceState)
  }
}