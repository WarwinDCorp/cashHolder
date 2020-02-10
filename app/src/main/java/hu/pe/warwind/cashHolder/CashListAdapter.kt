package hu.pe.warwind.cashHolder

import android.content.Context
import androidx.appcompat.view.menu.ActionMenuItemView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.pe.warwind.cashHolder.Room.CashTable

class CashListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<CashListAdapter.CashViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var cashTable = emptyList<CashTable>()

    inner class CashViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cashDate: TextView = itemView.findViewById(R.id.date)
        val cashCategory: TextView = itemView.findViewById(R.id.category)
        val cashSum: TextView = itemView.findViewById(R.id.sum)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CashViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return CashViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CashViewHolder, position: Int) {
        val current = cashTable[position]
        holder.cashDate.text = current.dateTime
        holder.cashCategory.text = current.category
        holder.cashSum.text = current.sum.toString()
    }

    internal fun setCash(cashTable: List<CashTable>) {
        this.cashTable = cashTable
        notifyDataSetChanged()
    }

    override fun getItemCount() = cashTable.size
    }
