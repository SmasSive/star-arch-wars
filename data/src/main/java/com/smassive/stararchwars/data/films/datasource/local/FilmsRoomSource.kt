package com.smassive.stararchwars.data.films.datasource.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.smassive.stararchwars.data.films.datasource.local.model.FilmEntity

@Database(entities = [FilmEntity::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class FilmsRoomSource : RoomDatabase() {

  companion object {
    const val DB_NAME = "stararchwars.db"
  }

  abstract fun filmDao(): FilmDao
}