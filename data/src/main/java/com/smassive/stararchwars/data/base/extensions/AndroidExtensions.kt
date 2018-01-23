package com.smassive.stararchwars.data.base.extensions

import android.util.Log

fun Any.logd(message: String) = Log.d(this.javaClass.simpleName, message)
fun Any.logw(message: String, error: Throwable? = null) = Log.w(this.javaClass.simpleName, message, error)
fun Any.loge(message: String, error: Throwable? = null) = Log.e(this.javaClass.simpleName, message, error)
