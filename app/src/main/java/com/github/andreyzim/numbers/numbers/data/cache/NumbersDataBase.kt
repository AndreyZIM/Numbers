package com.github.andreyzim.numbers.numbers.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NumberCache::class], version = 1)
abstract class NumbersDataBase : RoomDatabase() {

    abstract fun numbersDao(): NumbersDao
}