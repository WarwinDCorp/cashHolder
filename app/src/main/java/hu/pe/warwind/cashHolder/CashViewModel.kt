package hu.pe.warwind.cashHolder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import hu.pe.warwind.cashHolder.Room.CashRepository
import hu.pe.warwind.cashHolder.Room.CashTable
import hu.pe.warwind.cashHolder.Room.CashTableRoomDatabase
import kotlinx.coroutines.launch

class CashViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CashRepository

    val allCash: LiveData<List<CashTable>>
    val outCome: LiveData<Double>

    init {
        val cashTableDao = CashTableRoomDatabase.getDatabase(application, viewModelScope).cashTableDao()
        repository = CashRepository(cashTableDao)
        allCash = repository.allCash
        outCome = repository.outCome
    }

    fun insert(cashTable: CashTable) = viewModelScope.launch {
        repository.insert(cashTable)
    }
}