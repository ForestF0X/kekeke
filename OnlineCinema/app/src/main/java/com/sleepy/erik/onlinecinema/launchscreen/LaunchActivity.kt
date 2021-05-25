package com.sleepy.erik.onlinecinema.launchscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.sleepy.erik.onlinecinema.databinding.ActivityLaunchBinding
import com.sleepy.erik.onlinecinema.signupscreen.SignUpActivity

class LaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            launch()
            super.finish()
        }, 3000)
    }
    private fun launch(){
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        super.finish()
    }
}