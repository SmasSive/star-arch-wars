package com.smassive.stararchwars.data.films.datasource.local.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "film")
data class FilmEntity(@PrimaryKey(autoGenerate = true) var id: Int = 0,
                      var characters: List<String> = arrayListOf(),
                      var created: String = "",
                      var director: String = "",
                      var edited: String = "",
                      var opening_crawl: String = "",
                      var planets: List<String> = arrayListOf(),
                      var poster: String = "",
                      var producer: String = "",
                      var release_date: String = "",
                      var species: List<String> = arrayListOf(),
                      var starships: List<String> = arrayListOf(),
                      var title: String = "",
                      var url: String = "",
                      var vehicles: List<String> = arrayListOf())