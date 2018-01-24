package com.smassive.stararchwars.films.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.smassive.stararchwars.data.base.extensions.map
import com.smassive.stararchwars.data.films.model.Film
import com.smassive.stararchwars.data.films.model.Films
import com.smassive.stararchwars.data.films.repository.FilmsRepository
import com.smassive.stararchwars.films.model.FilmItem

class FilmsViewModel(application: Application,
                     private val filmsRepository: FilmsRepository) : AndroidViewModel(application) {

  fun getFilms(): LiveData<List<FilmItem>> = filmsRepository.getFilms().map { it.getFilmItems() }

  private fun Films.getFilmItems(): List<FilmItem> {
    return arrayListOf<FilmItem>().apply {
      for (film in this@getFilmItems.results) {
        add(film.getFilmItem())
      }
    }
  }

  private fun Film.getFilmItem() = FilmItem(getApplication(), poster, title, director)
}