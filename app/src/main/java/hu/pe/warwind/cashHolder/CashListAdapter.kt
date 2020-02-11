package hu.pe.warwind.cashHolder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.pe.warwind.cashHolder.Room.CashTable
import java.text.SimpleDateFormat
import java.util.*

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
        val currentSum: String
        val formatter = SimpleDateFormat("dd.MM.yyyy hh:mm")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = current.dateTime.toLong()
        val currentTime = formatter.format(calendar.time)

        when (current.isIncome) {
            true -> currentSum = "+ " + current.sum.toString()
            false -> currentSum = "- " + current.sum.toString()
        }
        holder.cashDate.text = currentTime
        holder.cashCategory.text = current.category
        holder.cashSum.text = currentSum
    }

    internal fun setCash(cashTable: List<CashTable>) {
        this.cashTable = cashTable
        notifyDataSetChanged()
    }

    override fun getItemCount() = cashTable.size
    }


