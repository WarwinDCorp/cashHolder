package hu.pe.warwind.cashHolder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hu.pe.warwind.cashHolder.Room.CashTable
import java.util.*

class BaseActivity : AppCompatActivity() {

    private val newCashActivityRequestCode = 1
    private lateinit var cashViewModel: CashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        //setSupportActionBar(Toolbar())

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = CashListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        //TODO както подтянуть инфу с формы
        cashViewModel = ViewModelProvider(this).get(CashViewModel::class.java)
        cashViewModel.allCash.observe(this, Observer {
            cash ->
                // Update the cached copy of the words in the adapter
                cash?.let { adapter.setCash(cash) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            Toast.makeText(applicationContext,"НЕ ТЫКОЙ!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        cashViewModel.outCome.observe(this, Observer {
                outCome ->
            outCome?.let {
                //Toast.makeText(applicationContext,outCome.toString(), Toast.LENGTH_LONG).show()
            }
            // Update the cached copy of the words in the adapter

        })

        //Toast.makeText(applicationContext,"123", Toast.LENGTH_LONG).show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newCashActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewCashActivity.EXTRA_CAT)?.let {
                val cashCat = data.getStringExtra(NewCashActivity.EXTRA_CAT)
                val cashSum = data.getStringExtra(NewCashActivity.EXTRA_SUM).toDouble()
                val cash = CashTable( 0, System.currentTimeMillis().toString(),cashCat, cashSum, false)
                cashViewModel.insert(cash)
            }
        } else {
            Toast.makeText(applicationContext,R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }
}
