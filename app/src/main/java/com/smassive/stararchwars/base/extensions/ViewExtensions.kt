package com.smassive.stararchwars.base.extensions

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.support.annotation.DimenRes
import android.view.View
import com.smassive.stararchwars.R

private const val LIST_TRANSLATION_DPS = 40
private const val PROPERTY_NAME_ALPHA = "alpha"
private const val PROPERTY_NAME_TRANSLATE_Y = "translationY"

fun getScreenWidthPixels(): Int = with(Resources.getSystem().displayMetrics) { this.widthPixels }

private val Int.px: Float
  get() = this * Resources.getSystem().displayMetrics.density

fun Context.getDimensionPixels(@DimenRes dimensionId: Int) = resources.getDimensionPixelSize(dimensionId)

fun View.enterListAnimation(context: Context) {
  AnimatorSet()
      .apply {
        playTogether(fadeInAnimation(context, this@enterListAnimation), translateAnimation(context, this@enterListAnimation))
      }
      .start()
}

private fun translateAnimation(context: Context, view: View) = ObjectAnimator
    .ofFloat(view, PROPERTY_NAME_TRANSLATE_Y, LIST_TRANSLATION_DPS.px, 0f)
    .setDuration(context.resources.getInteger(R.integer.translate_enter_transition_duration).toLong())

private fun fadeInAnimation(context: Context,
                            view: View,
                            animationDuration: Long = context.resources.getInteger(R.integer.alpha_enter_transition_duration).toLong()) =
    ObjectAnimator
        .ofFloat(view, PROPERTY_NAME_ALPHA, 0f, 1f)
        .setDuration(animationDuration)