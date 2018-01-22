package com.smassive.stararchwars.films.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.LazyKodeinAware
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import com.smassive.stararchwars.R
import com.smassive.stararchwars.base.extensions.enterListAnimation
import com.smassive.stararchwars.data.base.extensions.observeNonNull
import com.smassive.stararchwars.data.films.repository.FilmsRepository
import com.smassive.stararchwars.films.viewmodel.FilmsViewModel
import kotlinx.android.synthetic.main.activity_films.toolbar
import kotlinx.android.synthetic.main.content_films.filmsList
import kotlinx.android.synthetic.main.content_films.filmsLoading

class FilmsActivity : AppCompatActivity(), LazyKodeinAware {

  override val kodein = LazyKodein(appKodein)

  val filmsRepository: FilmsRepository by instance()
  val filmsAdapter: FilmsAdapter by with(this as AppCompatActivity).instance()

  private lateinit var filmsViewModel: FilmsViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_films)
    setSupportActionBar(toolbar)

    showLoading()
    configList()
    configViewModel()
  }

  private fun showLoading() {
    filmsList.visibility = View.GONE
    filmsLoading.visibility = View.VISIBLE
  }

  private fun hideLoading() {
    filmsList.visibility = View.VISIBLE
    filmsList.enterListAnimation(this)
    filmsLoading.visibility = View.GONE
  }

  private fun configList() {
    filmsList.apply {
      layoutManager = LinearLayoutManager(this@FilmsActivity)
      adapter = filmsAdapter
    }
  }

  private fun configViewModel() {
    filmsViewModel = ViewModelProviders.of(this).get(FilmsViewModel::class.java)
    filmsViewModel.init(filmsRepository)
    filmsViewModel.getFilms().observeNonNull(this) {
      filmsAdapter.addItems(it)
      hideLoading()
    }
  }

}
