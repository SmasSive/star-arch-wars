package com.smassive.stararchwars.films.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.smassive.stararchwars.data.films.model.Films
import com.smassive.stararchwars.data.films.repository.FilmsRepository

class FilmsViewModel : ViewModel() {

  private lateinit var filmsRepository: FilmsRepository

  fun init(filmsRepository: FilmsRepository) {
    this.filmsRepository = filmsRepository
  }

  fun getFilms(): LiveData<Films> = filmsRepository.getFilms()
}