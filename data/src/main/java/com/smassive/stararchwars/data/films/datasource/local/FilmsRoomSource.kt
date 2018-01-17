package com.smassive.stararchwars.data.films.datasource.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.smassive.stararchwars.data.films.datasource.local.model.FilmEntity

@Database(entities = [FilmEntity::class], version = 1)
abstract class FilmsRoomSource : RoomDatabase() {

  abstract fun filmDao(): FilmDao
}