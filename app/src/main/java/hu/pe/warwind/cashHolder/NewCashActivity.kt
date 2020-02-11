package hu.pe.warwind.cashHolder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class NewCashActivity : AppCompatActivity() {
    //TODO Переделать на автовыбор с экрана (возможно перечень вытягивать с RemoteConfig)
    private var currentCategory = "Другое"
    private var data =
        arrayOf("Автомобиль", "Продукты", "Спорт", "Кафе/Рестораны", "Комунальные", "Другое")
    private lateinit var editSumView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_cash)

        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = findViewById<View>(R.id.CategoryList) as Spinner
        spinner.adapter = adapter
        spinner.prompt = "Выберите категорию"

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentCategory = spinner.selectedItem.toString()
                Toast.makeText(applicationContext,currentCategory,Toast.LENGTH_LONG).show()
            }
        }

        editSumView = findViewById(R.id.editSum)
        //TODO настроить форму для возможности вносить данные
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editSumView.text)) {
                setResult(Activity.RESULT_CANCELED,replyIntent)
            } else {
                val sum = editSumView.text.toString()
                replyIntent.putExtra(EXTRA_CAT, currentCategory)
                replyIntent.putExtra(EXTRA_SUM, sum)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
    companion object {
        const val EXTRA_CAT = "com.example.android.categorysql.REPLY"
        const val EXTRA_SUM = "com.example.android.sumsql.REPLY"
    }
}
