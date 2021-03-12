package com.iiitpune.newsapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val good = findViewById<CardView>(R.id.good)
        val general = findViewById<CardView>(R.id.general)
        val business = findViewById<CardView>(R.id.business)
        val health = findViewById<CardView>(R.id.health)
        val science = findViewById<CardView>(R.id.science)
        val tech = findViewById<CardView>(R.id.tech)
        good.setOnClickListener {
            val intent: Intent = Intent(this, GoodActivity::class.java)
            val category: String = "Good News"
            val url = "https://saurav.tech/NewsAPI/top-headlines/category/general/in.json"
            intent.putExtra("category", category)
            intent.putExtra("url", url)
            startActivity(intent)
        }

        general.setOnClickListener {
            val category: String = "General"
            val intent: Intent = Intent(this, CategoryActivity::class.java)
            val url = "https://saurav.tech/NewsAPI/top-headlines/category/general/in.json"
            intent.putExtra("url", url)
            intent.putExtra("category", category)
            startActivity(intent)
        }

        business.setOnClickListener {
            val intent: Intent = Intent(this, CategoryActivity::class.java)
            val category: String = "Business"
            val url = "https://saurav.tech/NewsAPI/top-headlines/category/business/in.json"
            intent.putExtra("category", category)
            intent.putExtra("url", url)
            startActivity(intent)
        }

        health.setOnClickListener {
            val intent: Intent = Intent(this, CategoryActivity::class.java)
            val category: String = "Health"
            val url = "https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
            intent.putExtra("category", category)
            intent.putExtra("url", url)
            startActivity(intent)
        }

        science.setOnClickListener {
            val intent: Intent = Intent(this, CategoryActivity::class.java)
            val category: String = "Science"
            val url = "https://saurav.tech/NewsAPI/top-headlines/category/science/in.json"
            intent.putExtra("category", category)
            intent.putExtra("url", url)
            startActivity(intent)
        }

        tech.setOnClickListener {
            val intent: Intent = Intent(this, CategoryActivity::class.java)
            val category: String = "Technology"
            val url = "https://saurav.tech/NewsAPI/top-headlines/category/technology/in.json"
            intent.putExtra("category", category)
            intent.putExtra("url", url)
            startActivity(intent)
        }
    }

}