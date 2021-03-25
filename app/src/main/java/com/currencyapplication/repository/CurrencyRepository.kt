package com.currencyapplication.repository

import android.content.Context
import android.widget.Toast
import com.currencyapplication.database.AppDatabase
import com.currencyapplication.database.model.CurrencyInfo
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import kotlin.contracts.contract

class CurrencyRepository() {
    companion object {
        fun populate(context: Context) {
            Observable.just("currencies.json")
                .map {
                    var inputStream = context.assets.open(it)
                    val bufferedReader =
                        BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    val stringBuilder = StringBuilder()
                    while (true) {
                        val str: String = bufferedReader.readLine() ?: break
                        stringBuilder.append(str)
                    }
                    bufferedReader.close()
                    stringBuilder.toString()

                    if (AppDatabase.getInstance(context).currencyDao().countAll() == 0) {
                        Gson().fromJson(stringBuilder.toString(), Array<CurrencyInfo>::class.java).asList()
                            .forEach { currencyInfo ->
                                AppDatabase.getInstance(context).currencyDao()
                                    .insert(currencyInfo)
                            }
                    }

                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                .subscribe()
        }
    }
}