package com.sleepy.erik.onlinecinema.signinscreen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sleepy.erik.onlinecinema.common.LoginData
import com.sleepy.erik.onlinecinema.common.LoginResponse
import com.sleepy.erik.onlinecinema.common.RetrofitApi
import com.sleepy.erik.onlinecinema.databinding.ActivitySignInBinding
import com.sleepy.erik.onlinecinema.signupscreen.SignUpActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInActivity : AppCompatActivity(), View.OnClickListener {

    private var binding: ActivitySignInBinding? = null
    private lateinit var alertDialog: AlertDialog.Builder
    var sharedpreferences: SharedPreferences? = null
    val MyPREFERENCES = "myprefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)
        supportActionBar?.hide()
        binding?.loginBtn?.setOnClickListener(this)
        binding?.toRegBtn?.setOnClickListener(this)
        alertDialog = AlertDialog.Builder(this)
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)
    }

    private fun toReg() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        super.finish()
    }

    private fun login() {
        val email = binding?.emailText?.text.toString()
        val password = binding?.passText?.text.toString()
        val emailPattern = "[a-z0-9]+@[a-z]+\\.+[a-z]{1,3}+"

        if (email.matches(emailPattern.toRegex()) && password != "") {
            val apiService: RetrofitApi = RetrofitApi.Factory.create()
            val data: LoginData = LoginData(email, password)
            val call = apiService.login(data)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val editor: SharedPreferences.Editor? = sharedpreferences?.edit()
                    editor!!.putString("user_token", response.body()?.token.toString())
                    editor.apply()
                    editor.commit()
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    alertDialog.apply {
                        setTitle("Ошибка")
                        setMessage("Произошла ошибка на сервере, попробуйте позже")
                        setPositiveButton("Ок") { _, _ ->
                        }
                    }.create().show()
                    Log.d("netIssues", t.toString())
                }

            })
        } else if (email == "" && password == "") {
            alertDialog.apply {
                setTitle("Ошибка")
                setMessage("Введите адрес электронной почты и пароль")
                setPositiveButton("Ок") { _, _ ->
                }
            }.create().show()
        } else if (email == "") {
            alertDialog.apply {
                setTitle("Ошибка")
                setMessage("Введите адрес электронной почты")
                setPositiveButton("Ок") { _, _ ->
                }
            }.create().show()

        } else if (password == "") {
            alertDialog.apply {
                setTitle("Ошибка")
                setMessage("Введите пароль")
                setPositiveButton("Ок") { _, _ ->
                }
            }.create().show()
        } else if (!email.matches(emailPattern.toRegex())) {
            alertDialog.apply {
                setTitle("Ошибка")
                setMessage("Неправильный адрес электронной почты")
                setPositiveButton("Ок") { _, _ ->
                }
            }.create().show()
        } else {
            alertDialog.apply {
                setTitle("Ошибка")
                setMessage("Заполните все поля")
                setPositiveButton("Ок") { _, _ ->
                }
            }.create().show()
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding?.loginBtn -> {login()}
            binding?.toRegBtn -> {toReg()}
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
