package com.currencyapplication.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.currencyapplication.database.model.dao.CurrencyInfoDao
import com.currencyapplication.viewmodel.CurrencyListViewModel

class CurrencyListViewModelFactory(
    private val dataSource: CurrencyInfoDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyListViewModel::class.java)) {
            return CurrencyListViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
