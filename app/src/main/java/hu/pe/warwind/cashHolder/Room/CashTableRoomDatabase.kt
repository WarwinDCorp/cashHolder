package hu.pe.warwind.cashHolder.Room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [CashTable::class], version = 2, exportSchema = false)
public abstract class CashTableRoomDatabase : RoomDatabase() {

    abstract fun cashTableDao(): CashTableDao

    private class  CashTableDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    //clearDatabase(database.cashTableDao())
                    //getOutcome(database.cashTableDao())
                }
            }
        }
        suspend fun clearDatabase(cashTableDao: CashTableDao){
            cashTableDao.deleteAll()
        }

        suspend fun getOutcome(cashTableDao: CashTableDao){
            val outcome = cashTableDao.getOutcome()
            if (outcome.isNotEmpty()){
                Log.d("ERROR", outcome)
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: CashTableRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): CashTableRoomDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CashTableRoomDatabase::class.java,
                    "cash_database"
                )
                    .addCallback(CashTableDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                //return instance
                instance
            }
        }
    }
}

