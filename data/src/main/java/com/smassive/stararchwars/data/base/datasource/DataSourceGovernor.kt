package com.smassive.stararchwars.data.base.datasource

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.smassive.stararchwars.data.base.extensions.logd

abstract class DataSourceGovernor<ResultType, RequestType> {

  private val result = MediatorLiveData<ResultType>()

  init {
    start()
  }

  @MainThread
  private fun start() {
    loadFromLocal().run {
      result.addSource(this, {
        result.removeSource(this)
        if (shouldFetch(it)) {
          logd("Loding data from remote source")
          loadFromRemote(this)
        } else {
          logd("Loding data from local source")
          result.value = it
        }
      })
    }
  }

  @MainThread
  protected open fun shouldFetch(data: ResultType?): Boolean {
    return data == null
  }

  private fun loadFromRemote(localSource: LiveData<ResultType>) {
    createRemoteCall().run {
      result.addSource(this, {
        result.removeSource(this)
        Thread(Runnable {
          saveResponseFromRemote(it)
        }).start()
        result.addSource(localSource, { result.value = it })
      })
    }
  }

  @MainThread
  abstract fun loadFromLocal(): LiveData<ResultType>

  @MainThread
  abstract fun createRemoteCall(): LiveData<RequestType>

  @WorkerThread
  abstract fun saveResponseFromRemote(data: RequestType?)

  fun getAsLiveData(): LiveData<ResultType> = result
}