package com.currencyapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currencyapplication.database.model.CurrencyInfo
import com.currencyapplication.database.model.dao.CurrencyInfoDao
import io.reactivex.rxjava3.internal.operators.single.SingleDoOnEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyListViewModel(
    dataSource: CurrencyInfoDao
) : ViewModel() {

    companion object{
        val SORTING_ASCENDING = 1
        val SORTING_DESCENDING = -1
    }


    private val database = dataSource

    private var _currencies: MutableLiveData<List<CurrencyInfo>> =
        MutableLiveData<List<CurrencyInfo>>()
    var currencies: LiveData<List<CurrencyInfo>> = _currencies

    private var _sorting: MutableLiveData<Int> = MutableLiveData<Int>()
    var sorting: LiveData<Int> = _sorting

    private var _currency: MutableLiveData<CurrencyInfo> = MutableLiveData()
    var currency: LiveData<CurrencyInfo> = _currency
        set(value) {
            field = value
            resetClickedCurrency()
        }


    fun load() {
        viewModelScope.launch {
            var r: List<CurrencyInfo>
            withContext(Dispatchers.IO) {
                r = database.getAll()
            }
            _currencies.postValue(r)
            _sorting.postValue(SORTING_ASCENDING)
        }
    }

    fun sort() {
        _sorting.postValue(sorting.value?.let { 0.minus(it) })
        if (_sorting.value == SORTING_ASCENDING) {
            viewModelScope.launch {
                _currencies.postValue(_currencies.value?.sortedByDescending { info ->
                    info.name
                })
            }
        } else {
            viewModelScope.launch {
                _currencies.postValue(_currencies.value?.sortedBy { info ->
                    info.name
                })
            }
        }

    }

    fun setClickedCurrency(newCurrencyInfo: CurrencyInfo) {
        _currency.postValue(newCurrencyInfo)
    }
    private fun resetClickedCurrency() {
        _currency.postValue(null)
    }


}
