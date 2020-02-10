package hu.pe.warwind.cashHolder.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [CashTable::class], version = 1, exportSchema = false)
public abstract class CashTableRoomDatabase : RoomDatabase() {

    abstract fun cashTableDao(): CashTableDao

    private class  CashTableDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.cashTableDao())
                }
            }
        }

        suspend fun populateDatabase(cashTableDao: CashTableDao) {
            // Delete all content here.
            //cashTableDao.deleteAll()

            // Add sample words.
            //TODO удалить автозаполнение
            //val data = CashTable(1, Calendar.getInstance().time.toString(),"Автомобиль", 26.56, false)
            //cashTableDao.insert(data)
            //val data2 = CashTable(2, Calendar.getInstance().time.toString(),"Продукты", 112.48, false)
            //cashTableDao.insert(data2)

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
                    .build()
                INSTANCE = instance
                //return instance
                instance
            }
        }
    }
}

