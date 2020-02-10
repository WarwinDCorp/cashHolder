package hu.pe.warwind.cashHolder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import android.text.Editable
import android.text.TextWatcher


import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    val client by lazy {
        DOLApiClient.create()
    }

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtPassword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.toString().contains("\n") ){
                    Toast.makeText(applicationContext,s,Toast.LENGTH_LONG).show()
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

            }
        })


        btnLogin.setOnClickListener {
            val intent = Intent(this, BaseActivity::class.java)
            startActivity(intent)
            //makeLogin()
        }

    }

    private fun makeLogin() {
        val username = txtLogin.text.toString()
        val password = txtPassword.text.toString()
        val data = LoginRequest(username,password,"login")
        disposable = client.getToken(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    Log.e("ERROR_TEXT", "" + result.errorText)
                    if (result.errorText.isNullOrBlank()){

                        val sharedPreferences = getSharedPreferences("tokens", Activity.MODE_PRIVATE).edit()
                        sharedPreferences.putString("token", result.data)
                        sharedPreferences.apply()

                        Toast.makeText(applicationContext,result.data,Toast.LENGTH_LONG).show()
                        val intent = Intent(this, BaseActivity::class.java)
                        intent.putExtra("login", username)
                        startActivity(intent)
                    }else{
                        Toast.makeText(applicationContext,result.errorText,Toast.LENGTH_LONG).show()
                    }
                },
                { error ->
                    Log.e("ERROR", error.message)
                }
            )
    }
}
