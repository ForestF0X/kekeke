package com.sleepy.erik.onlinecinema.signupscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.sleepy.erik.onlinecinema.common.RegisterData
import com.sleepy.erik.onlinecinema.common.RetrofitApi
import com.sleepy.erik.onlinecinema.databinding.ActivitySignUpBinding
import com.sleepy.erik.onlinecinema.signinscreen.SignInActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private var binding: ActivitySignUpBinding? = null
    private lateinit var alertDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)
        supportActionBar?.hide()
        binding?.regBtn?.setOnClickListener(this)
        binding?.toLoginBtn?.setOnClickListener(this)
        alertDialog = AlertDialog.Builder(this)
    }

    private fun toLogin() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        super.finish()
    }

    private fun reg() {
        val email = binding?.emailText?.text.toString()
        val password = binding?.passText?.text.toString()
        val firstName = binding?.firstNameText?.text.toString()
        val lastName = binding?.lastNameText?.text.toString()
        val passCheck = binding?.passCheckText?.text.toString()
        val emailPattern = "[a-z0-9]+@[a-z]+\\.+[a-z]{1,3}+"
        if (email.matches(emailPattern.toRegex()) && password != "" && passCheck != "" && firstName != "" && lastName != "" && password == passCheck) {
            val apiService: RetrofitApi = RetrofitApi.Factory.create()
            val data: RegisterData = RegisterData(email, password, firstName, lastName)
            val call = apiService.registration(data)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {

                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
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
        } else if (firstName == ""&& lastName == "") {
            alertDialog.apply {
                setTitle("Ошибка")
                setMessage("Вы не ввели имя и фамилию")
                setPositiveButton("Ок") { _, _ ->
                }
            }.create().show()
        } else if (firstName == "") {
            alertDialog.apply {
                setTitle("Ошибка")
                setMessage("Вы не ввели имя")
                setPositiveButton("Ок") { _, _ ->
                }
            }.create().show()
        } else if (lastName == "") {
            alertDialog.apply {
                setTitle("Ошибка")
                setMessage("Вы не ввели фамилию")
                setPositiveButton("Ок") { _, _ ->
                }
            }.create().show()
        } else if (password != passCheck) {
            alertDialog.apply {
                setTitle("Ошибка")
                setMessage("Пароли не совпадают")
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
            binding?.regBtn -> {reg()}
            binding?.toLoginBtn -> {toLogin()}
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}