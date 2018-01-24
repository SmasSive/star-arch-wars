package com.smassive.stararchwars.films.viewmodel

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.smassive.stararchwars.data.films.repository.FilmsRepository

class FilmsViewModelFactory(private val application: Application,
                            private val filmsRepository: FilmsRepository) : ViewModelProvider.Factory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(FilmsViewModel::class.java)) {
      return FilmsViewModel(application, filmsRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}