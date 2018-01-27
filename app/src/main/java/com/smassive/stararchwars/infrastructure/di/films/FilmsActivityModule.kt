package com.smassive.stararchwars.infrastructure.di.films

import android.arch.lifecycle.ViewModelProviders
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.scopedSingleton
import com.github.salomonbrys.kodein.singleton
import com.smassive.stararchwars.films.view.FilmsActivity
import com.smassive.stararchwars.films.view.FilmsAdapter
import com.smassive.stararchwars.films.viewmodel.FilmsViewModel
import com.smassive.stararchwars.films.viewmodel.FilmsViewModelFactory

fun filmsActivityModule(filmsActivity: FilmsActivity) = Kodein.Module(allowSilentOverride = true) {
  bind<FilmsAdapter>() with scopedSingleton(androidActivityScope) {
    FilmsAdapter()
  }

  bind<FilmsViewModelFactory>() with singleton {
    FilmsViewModelFactory(instance(), instance())
  }

  bind<FilmsViewModel>() with scopedSingleton(androidActivityScope) {
    ViewModelProviders.of(filmsActivity, instance<FilmsViewModelFactory>()).get(FilmsViewModel::class.java)
  }
}