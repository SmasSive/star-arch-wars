package com.smassive.stararchwars.films.model

import android.content.Context

data class FilmItem(private val context: Context,
                    val filmPosterUrl: String,
                    val filmTitle: String,
                    val filmDirector: String)