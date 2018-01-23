package com.smassive.stararchwars.data.films.datasource.local

import android.arch.persistence.room.TypeConverter

object StringListConverter {

  private const val SEPARATOR = ","

  @TypeConverter
  @JvmStatic
  fun fromString(value: String): List<String> = value.split(SEPARATOR).toMutableList()

  @TypeConverter
  @JvmStatic
  fun toString(value: List<String>): String = value.joinToString(SEPARATOR)
}