package com.smassive.stararchwars.data.films.datasource.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.smassive.stararchwars.data.films.datasource.local.model.FilmEntity

@Dao
interface FilmDao {

  @Query("SELECT * FROM film")
  fun loadAll(): LiveData<List<FilmEntity>>

  @Insert
  fun saveAll(filmEntities: List<FilmEntity>)
}