package hu.pe.warwind.cashHolder.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CashTableDao {

    @Query("SELECT * FROM cash_table")
    fun getIncome(): LiveData<List<CashTable>>

    @Query("SELECT SUM(sum) FROM cash_table WHERE isIncome = :param ORDER BY dateTime DESC")
    fun getOutcome(param: Boolean = false): LiveData<Double>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(CashTable: CashTable)

    @Query("DELETE FROM cash_table")
    suspend fun deleteAll()

}