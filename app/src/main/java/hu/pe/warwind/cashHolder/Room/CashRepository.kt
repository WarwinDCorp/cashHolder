package hu.pe.warwind.cashHolder.Room

import androidx.lifecycle.LiveData

class CashRepository(private val cashTableDao: CashTableDao) {

    val allCash: LiveData<List<CashTable>> = cashTableDao.getIncome()
    val outCome: LiveData<Double> = cashTableDao.getOutcome()

    suspend fun insert(cashTable: CashTable) {
        cashTableDao.insert(cashTable)
    }
}