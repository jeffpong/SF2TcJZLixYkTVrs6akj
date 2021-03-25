package com.currencyapplication.database.model.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.currencyapplication.database.model.CurrencyInfo

@Dao
interface CurrencyInfoDao {
    @Insert
    fun insert(currencyInfo: CurrencyInfo)
    @Query("SELECT COUNT(*) FROM currencyinfo")
    fun countAll(): Int
    @Query("SELECT * FROM currencyinfo ORDER BY name ASC")
    fun getAll(): List<CurrencyInfo>
}