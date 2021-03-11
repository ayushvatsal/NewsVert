package com.iiitpune.newsapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen

class GoodActivity : AppCompatActivity() ,NewsItemClicked{

    private lateinit var mAdapter: NewsListAdapter
    private lateinit var skeleton: SkeletonScreen
    private lateinit var back_button: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_good)
        var category = findViewById<TextView>(R.id.category)
        category.text = intent.getStringExtra("category").toString()

        back_button = findViewById<ImageView>(R.id.back_button)
        back_button.setOnClickListener{
            onBackPressed()
        }

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter= NewsListAdapter( this)
        recycler.adapter = mAdapter
        skeleton =  Skeleton.bind(recycler).adapter(mAdapter).load(R.layout.item_news).show()
    }
    private fun fetchData(){
        val url = intent.getStringExtra("url")
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,

                Response.Listener {
                    val newsJsonArray = it.getJSONArray("articles")
                    val newsArray = ArrayList<News>()
                    for(i in 0 until newsJsonArray.length()){
                        val newsJsonObject = newsJsonArray.getJSONObject(i)
                        val news = News(
                                newsJsonObject.getString("title"),
                                newsJsonObject.getString("author"),
                                newsJsonObject.getString("url"),
                                newsJsonObject.getString("urlToImage"),
                                newsJsonObject.getString("description")
                        )
                        val x = newsJsonObject.getString("title")
                        newsArray.add(news)
                        skeleton.hide()
                    }
                    mAdapter.updateNews(newsArray)
                },
                Response.ErrorListener {

                }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}