package com.smassive.stararchwars.data.films.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.google.firebase.database.DataSnapshot
import com.smassive.stararchwars.data.films.datasource.FilmsFirebaseSource
import com.smassive.stararchwars.data.films.model.Film
import com.smassive.stararchwars.data.films.model.Films

class FilmsRepository(private val filmsFirebaseSource: FilmsFirebaseSource) {

  fun getFilms(): LiveData<Films> = Transformations.map(filmsFirebaseSource.getFilms(), mapFilms())

  private fun mapFilms(): (DataSnapshot) -> Films {
    return { dataSnapshot -> Films(dataSnapshot.getCount(), dataSnapshot.getFilms()) }
  }

  private fun DataSnapshot.getCount() = this.child("count").getValue(Int::class.java) ?: 0

  private fun DataSnapshot.getFilms(): List<Film> {
    return arrayListOf<Film>().apply {
      for (child in this@getFilms.child("results").children) {
        child.getValue(Film::class.java)?.let { add(it) }
      }
    }
  }
}