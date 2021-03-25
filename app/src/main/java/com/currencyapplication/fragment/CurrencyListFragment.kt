package com.currencyapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.currencyapplication.R
import com.currencyapplication.adapter.CurrencyInfoAdapter
import com.currencyapplication.database.AppDatabase
import com.currencyapplication.databinding.FragmentCurrencyListBinding
import com.currencyapplication.viewmodel.CurrencyListViewModel
import com.currencyapplication.viewmodel.factory.CurrencyListViewModelFactory


class CurrencyListFragment : Fragment() {

    private val viewModel: CurrencyListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCurrencyListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_currency_list, container, false
        )

        val currencyAdapter = CurrencyInfoAdapter{
            viewModel.setClickedCurrency(it)
        }
        binding.recyclerView.adapter = currencyAdapter

        val dividerItemDecoration = DividerItemDecoration(
            binding.recyclerView.context,
            (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
        )
        context?.let {
            ContextCompat.getDrawable(it, R.drawable.list_divider)?.let { drawable ->
                dividerItemDecoration.setDrawable(
                    drawable
                )
            }
        }

        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        viewModel.currencies.observe(viewLifecycleOwner, Observer {
            currencyAdapter.data = it
        })

        binding.lifecycleOwner = this

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CurrencyListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}