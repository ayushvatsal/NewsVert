package com.iiitpune.newsapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class splash : AppCompatActivity() {
    lateinit var handler: Handler
    lateinit var logo: ImageView
    lateinit var appName: ImageView
    lateinit var splashImg: ImageView
    lateinit var lottieAnimationView: LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        logo = findViewById<ImageView>(R.id.logo)
        appName = findViewById<ImageView>(R.id.app_name)
        splashImg = findViewById<ImageView>(R.id.img)
        lottieAnimationView = findViewById<LottieAnimationView>(R.id.lottie)

        splashImg.animate().translationY(-2800f).setDuration(900).setStartDelay(4000)
        logo.animate().translationY(1850f).setDuration(900).setStartDelay(4000)
        appName.animate().translationY(1850f).setDuration(900).setStartDelay(4000)
        lottieAnimationView.animate().translationY(1850f).setDuration(900).setStartDelay(4000)

        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4880)
    }
}