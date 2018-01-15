package com.smassive.stararchwars.base.extensions

import android.content.Context
import android.content.res.Resources
import android.support.annotation.DimenRes

fun getScreenWidthPixels(): Int = with(Resources.getSystem().displayMetrics) { this.widthPixels }

fun Context.getDimensionPixels(@DimenRes dimensionId: Int) = resources.getDimensionPixelSize(dimensionId)