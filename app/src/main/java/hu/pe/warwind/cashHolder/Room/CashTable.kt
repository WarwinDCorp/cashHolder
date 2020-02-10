package hu.pe.warwind.cashHolder.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "cash_table")
data class CashTable(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo (name = "Дата")
    val dateTime: String,

    @ColumnInfo (name = "Категория")
    val category: String,

    @ColumnInfo (name = "sum")
    val sum: Double,

    @ColumnInfo (name = "Доход ли")
    val isIncome: Boolean
)