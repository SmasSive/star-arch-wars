package com.smassive.stararchwars.films.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.smassive.stararchwars.base.extensions.map
import com.smassive.stararchwars.data.films.model.Film
import com.smassive.stararchwars.data.films.repository.FilmsRepository
import com.smassive.stararchwars.films.model.FilmItem

class FilmsViewModel(application: Application) : AndroidViewModel(application) {

  private lateinit var filmsRepository: FilmsRepository

  fun init(filmsRepository: FilmsRepository) {
    this.filmsRepository = filmsRepository
  }

  fun getFilms(): LiveData<List<FilmItem>> = filmsRepository.getFilms().map {
    arrayListOf<FilmItem>().apply {
      for (film in it.results) {
        add(film.getFilmItem())
      }
    }
  }

  private fun Film.getFilmItem() = FilmItem(getApplication(), poster, title, director)
}