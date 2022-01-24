package com.example.newsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NewsItemClicked {
    lateinit var adaptor : NewsApdator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RecyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        adaptor = NewsApdator(this)
        RecyclerView.adapter = adaptor
    }
    private fun fetchData(){
        val url = "https://newsdata.io/api/1/news?apikey=pub_8304b51307b0df162459f46f647112b4cde&q=usa"
        val jsonobjectrequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener {
                    val jsonArray = it.getJSONArray("results")
                    val arraynews = ArrayList<News>()
                    for (i in 0 until jsonArray.length()) {
                        val jsonObjectArray = jsonArray.getJSONObject(i)
                        val news = News(
                                jsonObjectArray.getString("title"),
                                jsonObjectArray.getString("link"),
                                jsonObjectArray.getString("image_url"),
                                jsonObjectArray.getString("source_id"),
                        )
                        arraynews.add(news)
                    }
                    adaptor.updateNews(arraynews)
                },
                {
                    Toast.makeText(this, "You click", Toast.LENGTH_SHORT).show()
                }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonobjectrequest)
    }

    override fun OnItemClicked(item: News) {
        var url = item.link
//        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        val builder = CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}