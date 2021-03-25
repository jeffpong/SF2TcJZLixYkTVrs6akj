package com.currencyapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.currencyapplication.BuildConfig
import com.currencyapplication.database.model.CurrencyInfo
import com.currencyapplication.database.model.dao.CurrencyInfoDao

@Database(entities = [CurrencyInfo::class], version = 3)
abstract class AppDatabase: RoomDatabase() {
    abstract fun currencyDao(): CurrencyInfoDao
    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java, BuildConfig.APPLICATION_ID
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}