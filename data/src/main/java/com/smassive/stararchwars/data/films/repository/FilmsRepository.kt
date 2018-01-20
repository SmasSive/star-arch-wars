package com.smassive.stararchwars.data.films.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.google.firebase.database.DataSnapshot
import com.smassive.stararchwars.data.base.extensions.map
import com.smassive.stararchwars.data.films.datasource.local.FilmDao
import com.smassive.stararchwars.data.films.datasource.local.model.FilmEntity
import com.smassive.stararchwars.data.films.datasource.remote.FilmsFirebaseSource
import com.smassive.stararchwars.data.films.model.Film
import com.smassive.stararchwars.data.films.model.Films

class FilmsRepository(private val filmsFirebaseSource: FilmsFirebaseSource,
                      private val filmDao: FilmDao) {

  private val result = MediatorLiveData<Films>()

  fun getFilms(): LiveData<Films> {
    val localSource = loadFromLocal()
    result.addSource(localSource, { data ->
      result.removeSource(localSource)
      if (shouldFetch(data)) {
        loadFromRemote(localSource)
      } else {
        result.value = data
      }
    })
    return result
  }

  private fun loadFromLocal(): LiveData<Films> {
    return filmDao.loadAll().map { Films(it.size, it.getFilms()) }
  }

  private fun shouldFetch(data: Films?): Boolean {
    return data == null || data.count == 0
  }

  private fun loadFromRemote(localSource: LiveData<Films>) {
    val remoteSource = filmsFirebaseSource.getFilms()
    result.addSource(remoteSource, { data ->
      result.removeSource(remoteSource)
      saveFilmsFromRemote(data)
      result.addSource(localSource, { result.value = it })
    })
  }

  private fun saveFilmsFromRemote(data: DataSnapshot?) {
    Thread(Runnable {
      data?.getFilms()?.map { it.getFilmEntity() }?.apply { filmDao.saveAll(this) }
    }).start()
  }

  private fun List<FilmEntity>.getFilms(): List<Film> {
    return arrayListOf<Film>().apply {
      this@getFilms
          .map {
            Film(it.characters, it.created, it.director, it.edited, it.opening_crawl, it.planets, it.poster, it.producer, it.release_date,
                it.species, it.starships, it.title, it.url, it.vehicles)
          }
          .apply {
            addAll(this)
          }
    }
  }

  private fun DataSnapshot.getFilms(): List<Film> {
    return arrayListOf<Film>().apply {
      this@getFilms.child("results").children
          .map { it.getValue(Film::class.java)?.let { add(it) } }
    }
  }

  private fun Film.getFilmEntity(): FilmEntity {
    return FilmEntity(characters = characters, created = created, director = director, edited = edited, opening_crawl = opening_crawl,
        planets = planets, poster = poster, producer = producer, release_date = release_date, species = species, starships = starships,
        title = title, url = url, vehicles = vehicles)
  }
}
