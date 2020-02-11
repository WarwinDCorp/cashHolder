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
                cash?.let { adapter.setCash(cash) }
        })
        val outcome = cashViewModel.getOutcome()
        Toast.makeText(applicationContext,outcome.toString(), Toast.LENGTH_LONG).show()
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@BaseActivity, NewCashActivity::class.java)
            startActivityForResult(intent, newCashActivityRequestCode)
        }
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
        } else if (resultCode == Activity.RESULT_CANCELED){
            Toast.makeText(applicationContext,"Ничего не записано", Toast.LENGTH_LONG).show()
        }
    }
}
