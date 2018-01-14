package com.smassive.stararchwars.films.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.smassive.stararchwars.R
import com.smassive.stararchwars.films.model.FilmItem
import kotlinx.android.synthetic.main.item_film.view.filmDirector
import kotlinx.android.synthetic.main.item_film.view.filmPoster
import kotlinx.android.synthetic.main.item_film.view.filmTitle

class FilmsAdapter : RecyclerView.Adapter<FilmsAdapter.FilmViewHolder>() {

  private val films: MutableList<FilmItem> = mutableListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder =
      FilmViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false))

  override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
    holder.bind(films[position])
  }

  override fun getItemCount() = films.size

  fun addItems(films: List<FilmItem>) {
    this.films.addAll(films)
    notifyDataSetChanged()
  }

  class FilmViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(filmItem: FilmItem) {
      with(filmItem) {
        Glide.with(itemView.context)
            .load(filmPosterUrl)
            .apply(RequestOptions.centerCropTransform())
            .into(itemView.filmPoster)
        itemView.filmTitle.text = filmTitle
        itemView.filmDirector.text = filmDirector
      }
    }
  }
}