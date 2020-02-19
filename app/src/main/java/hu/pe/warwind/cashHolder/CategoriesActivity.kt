package hu.pe.warwind.cashHolder

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import hu.pe.warwind.cashHolder.Room.CashTable

class CategoriesActivity : AppCompatActivity() {

    private val newCashActivityRequestCode = 1
    private lateinit var cashViewModel: CashViewModel
    private var realIncome: Double = 0.00
    private var realOutcome: Double = 0.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        cashViewModel = ViewModelProvider(this).get(CashViewModel::class.java)
        val products = findViewById<ImageView>(R.id.categoryProducts)
        val auto = findViewById<ImageView>(R.id.categoryAuto)
        val cafe = findViewById<ImageView>(R.id.categoryCafe)
        val home = findViewById<ImageView>(R.id.categoryHome)
        val mobile = findViewById<ImageView>(R.id.categoryMobile)
        val sport = findViewById<ImageView>(R.id.categorySport)
        val other = findViewById<ImageView>(R.id.categoryOther)

        val buttons: Array<ImageView> = arrayOf(products,auto,cafe,home,mobile,sport,other)
        val intent = Intent(this@CategoriesActivity, NewCashActivity::class.java)
        for(button in buttons){
            button.setOnClickListener {
                intent.putExtra("category", button.contentDescription)
                startActivityForResult(intent, newCashActivityRequestCode)
            }
        }

        val descriptionActivity = Intent(this, BaseActivity::class.java)
        findViewById<TextView>(R.id.totalOutcome).setOnClickListener {
            startActivity(descriptionActivity)
        }

        findViewById<TextView>(R.id.totalIncome).setOnClickListener {
            startActivity(descriptionActivity)
        }

    }
    override fun onResume() {
        super.onResume()
        //refreshCome()
        //findViewById<TextView>(R.id.totalIncome).text = realInCome.toString()
        //findViewById<TextView>(R.id.totalOutcome).text = (realInCome - realOutCome).toString()

    }

    override fun onStart() {
        super.onStart()
        refreshCome()
        findViewById<TextView>(R.id.totalIncome).text = realIncome.toString()
            findViewById<TextView>(R.id.totalOutcome).text = "- " + realOutcome.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newCashActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewCashActivity.EXTRA_CAT)?.let {
                val cashCat = data.getStringExtra(NewCashActivity.EXTRA_CAT)
                val cashSum = data.getStringExtra(NewCashActivity.EXTRA_SUM).toDouble()
                val cash =
                    CashTable(0, System.currentTimeMillis().toString(), cashCat, cashSum, false)
                cashViewModel.insert(cash)
                refreshCome()
                //Toast.makeText(applicationContext,cashCat + ": " + cashSum, Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(applicationContext,R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }

    private fun refreshCome(){
        cashViewModel.inCome.observe(this, Observer {
                inCome ->
            inCome?.let {
                realIncome = inCome
            }
        })
        cashViewModel.outCome.observe(this, Observer {
                outCome ->
            outCome?.let {
                realOutcome = outCome
            }
        })
    }
}
