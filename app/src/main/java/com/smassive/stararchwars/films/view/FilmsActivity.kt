package com.smassive.stararchwars.films.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.smassive.stararchwars.R
import com.smassive.stararchwars.data.base.extensions.observeNonNull
import com.smassive.stararchwars.data.films.datasource.FilmsFirebaseSource
import com.smassive.stararchwars.data.films.repository.FilmsRepository
import com.smassive.stararchwars.films.viewmodel.FilmsViewModel
import kotlinx.android.synthetic.main.activity_films.toolbar
import kotlinx.android.synthetic.main.content_films.filmsList

class FilmsActivity : AppCompatActivity() {

  private lateinit var filmsViewModel: FilmsViewModel
  private val filmsAdapter = FilmsAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_films)
    setSupportActionBar(toolbar)

    filmsList.apply {
      layoutManager = LinearLayoutManager(this@FilmsActivity)
      adapter = filmsAdapter
    }

    filmsViewModel = ViewModelProviders.of(this).get(FilmsViewModel::class.java)
    filmsViewModel.init(FilmsRepository(FilmsFirebaseSource()))
    filmsViewModel.getFilms().observeNonNull(this) { filmsAdapter.addItems(it) }
  }

}
