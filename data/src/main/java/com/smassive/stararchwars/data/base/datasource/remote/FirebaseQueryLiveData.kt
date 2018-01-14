package com.smassive.stararchwars.data.base.datasource.remote

import android.arch.lifecycle.LiveData
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class FirebaseQueryLiveData(private val query: Query) : LiveData<DataSnapshot>() {

  companion object {
    private const val LOG_TAG = "FirebaseQueryLiveData"
  }

  private val listener = DataValueEventListener()

  override fun onActive() {
    Log.d(LOG_TAG, "onActive")
    query.addValueEventListener(listener)
  }

  override fun onInactive() {
    Log.d(LOG_TAG, "onInactive")
    query.removeEventListener(listener)
  }

  private inner class DataValueEventListener : ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
      value = dataSnapshot
    }

    override fun onCancelled(databaseError: DatabaseError) {
      Log.e(LOG_TAG, "Can't listen to query " + query, databaseError.toException())
    }
  }

}