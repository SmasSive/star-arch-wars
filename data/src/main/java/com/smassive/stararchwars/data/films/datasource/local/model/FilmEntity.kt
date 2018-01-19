package com.smassive.stararchwars.data.films.datasource.local.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "film")
data class FilmEntity(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                      val characters: List<String> = arrayListOf(),
                      val created: String = "",
                      val director: String = "",
                      val edited: String = "",
                      val opening_crawl: String = "",
                      val planets: List<String> = arrayListOf(),
                      val poster: String = "",
                      val producer: String = "",
                      val release_date: String = "",
                      val species: List<String> = arrayListOf(),
                      val starships: List<String> = arrayListOf(),
                      val title: String = "",
                      val url: String = "",
                      val vehicles: List<String> = arrayListOf())