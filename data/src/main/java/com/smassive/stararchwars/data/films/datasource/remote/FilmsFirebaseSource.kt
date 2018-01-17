package com.smassive.stararchwars.data.films.datasource.remote

import android.arch.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.smassive.stararchwars.data.base.datasource.remote.FirebaseQueryLiveData

class FilmsFirebaseSource {

  companion object {
    private val databaseReference = FirebaseDatabase.getInstance().getReference("/films")
  }

  private val liveData = FirebaseQueryLiveData(databaseReference)

  fun getFilms(): LiveData<DataSnapshot> = liveData
}