package com.smassive.stararchwars.base.extensions

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations

fun <T, R> LiveData<T>.map(transformation: (T) -> R): LiveData<R> {
  return Transformations.map(this, transformation)
}

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, f: (T) -> Unit) {
  this.observe(owner, android.arch.lifecycle.Observer<T> { t -> t?.let(f) })
}