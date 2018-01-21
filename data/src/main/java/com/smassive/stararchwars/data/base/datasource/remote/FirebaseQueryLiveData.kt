package com.smassive.stararchwars.data.base.datasource.remote

import android.arch.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.smassive.stararchwars.data.base.extensions.logd
import com.smassive.stararchwars.data.base.extensions.loge

class FirebaseQueryLiveData(private val query: Query) : LiveData<DataSnapshot>() {

  private val listener = DataValueEventListener()

  override fun onActive() {
    logd("onActive")
    query.addValueEventListener(listener)
  }

  override fun onInactive() {
    logd("onInactive")
    query.removeEventListener(listener)
  }

  private inner class DataValueEventListener : ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
      value = dataSnapshot
    }

    override fun onCancelled(databaseError: DatabaseError) {
      loge("Can't listen to query $query", databaseError.toException())
    }
  }

}