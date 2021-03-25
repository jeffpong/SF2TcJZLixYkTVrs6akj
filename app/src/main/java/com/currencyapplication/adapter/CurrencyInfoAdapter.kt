package com.currencyapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.currencyapplication.R
import com.currencyapplication.database.model.CurrencyInfo

class CurrencyInfoAdapter(private val onItemClickListener: ((CurrencyInfo)->Unit)) : RecyclerView.Adapter<CurrencyInfoAdapter.ViewHolder>() {

    var data = listOf<CurrencyInfo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onItemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val initial: TextView = itemView.findViewById(R.id.initial)
        val name: TextView = itemView.findViewById(R.id.name)
        val symbol: TextView = itemView.findViewById(R.id.symbol)

        fun bind(item: CurrencyInfo, onItemClickListener: ((CurrencyInfo)->Unit)) {
            initial.text = item.name.substring(0, 1)
            name.text = item.name
            symbol.text = item.symbol
            itemView.setOnClickListener {
                onItemClickListener.invoke(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.item_currency, parent, false)
                return ViewHolder(view)
            }
        }
    }
}