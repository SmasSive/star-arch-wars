package com.smassive.stararchwars.base.extensions

import android.content.res.Resources

fun getScreenWidthPixels(): Int = with(Resources.getSystem().displayMetrics) { this.widthPixels }

val Int.px: Int
  get() = (this * Resources.getSystem().displayMetrics.density).toInt()