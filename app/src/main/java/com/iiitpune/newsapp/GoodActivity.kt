package com.iiitpune.newsapp

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.nabilmh.lottieswiperefreshlayout.LottieSwipeRefreshLayout
import org.tensorflow.lite.task.text.nlclassifier.NLClassifier
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class GoodActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter
    private lateinit var skeleton: SkeletonScreen
    private lateinit var back_button: ImageView
    private var executorService: ExecutorService? = null
    private var textClassifier: NLClassifier? = null
    private var path: String = "text_classification_v2.tflite"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_good)
        var category = findViewById<TextView>(R.id.category)
        category.text = intent.getStringExtra("category").toString()

        back_button = findViewById<ImageView>(R.id.back_button)
        back_button.setOnClickListener {
            onBackPressed()
        }
        executorService = Executors.newSingleThreadExecutor()
        textClassifier = NLClassifier.createFromFile(this, path)

        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        recycler.adapter = mAdapter
        skeleton = Skeleton.bind(recycler).adapter(mAdapter).load(R.layout.item_news).show()
        var reload : LottieSwipeRefreshLayout = findViewById<LottieSwipeRefreshLayout>(R.id.reload)
        reload.setOnRefreshListener{
            skeleton = Skeleton.bind(recycler).adapter(mAdapter).load(R.layout.item_news).show()
            Handler().postDelayed({
                fetchData()
                mAdapter = NewsListAdapter(this)
                recycler.adapter = mAdapter
                reload.isRefreshing = false
            },1500)

        }

    }

    private fun fetchData() {
        val url = intent.getStringExtra("url")
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,

                Response.Listener {
                    val newsJsonArray = it.getJSONArray("articles")
                    val newsArray = ArrayList<News>()
                    for (i in 0 until newsJsonArray.length()) {
                        val newsJsonObject = newsJsonArray.getJSONObject(i)
                        val news = News(
                                newsJsonObject.getString("title"),
                                newsJsonObject.getString("author"),
                                newsJsonObject.getString("url"),
                                newsJsonObject.getString("urlToImage"),
                                newsJsonObject.getString("description")
                        )
                        val x = newsJsonObject.getString("description")

                        var con1: Float? = classify(x)
                        Log.d("classify",x)
                        Log.d("classify",con1.toString())
                        if (con1 != null) {
                            if (con1 >= 0.5) {
                                newsArray.add(news)
                            }
                        }
                        skeleton.hide()
                    }
                    mAdapter.updateNews(newsArray)
                },
                Response.ErrorListener {

                }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    private fun classify(text: String): Float? {
        var con: Float? = 0.0f
        val results = textClassifier!!.classify(text)
        for (i in results) {
            if (i.label == "Positive") {
                con = i.score
            }
        }
        return con
    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}