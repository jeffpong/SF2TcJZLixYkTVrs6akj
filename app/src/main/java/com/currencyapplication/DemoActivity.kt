package com.currencyapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.currencyapplication.database.AppDatabase
import com.currencyapplication.databinding.ActivityDemoBinding
import com.currencyapplication.fragment.CurrencyListFragment
import com.currencyapplication.repository.CurrencyRepository
import com.currencyapplication.viewmodel.CurrencyListViewModel
import com.currencyapplication.viewmodel.factory.CurrencyListViewModelFactory

class DemoActivity : AppCompatActivity() {
    lateinit var viewModel: CurrencyListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityDemoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<CurrencyListFragment>(R.id.fragment_container_view)
            }
        }
        viewModel = ViewModelProvider(
            this, CurrencyListViewModelFactory(
                AppDatabase.getInstance(
                    requireNotNull(
                        this
                    ).application
                ).currencyDao()
            )
        ).get(
            CurrencyListViewModel::class.java
        )
        var toast: Toast? = null
        viewModel.currency.observe(this, { currencyInfo ->
            if (currencyInfo == null) return@observe
            toast?.cancel()
            toast = Toast.makeText(this, currencyInfo.name, Toast.LENGTH_SHORT)
            toast?.show()

        })
        binding.viewModel = viewModel
        binding.lifecycleOwner = this



        populateRoom()
    }

    private fun populateRoom() {
        CurrencyRepository.populate(applicationContext)
    }

}