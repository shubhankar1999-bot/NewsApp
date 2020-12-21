package com.shubhankarpandey_developer.news

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_pdf.*
import java.util.*
import kotlin.collections.ArrayList

class PdfActivity : AppCompatActivity(), NewbieClicked {
    private val list :ArrayList<Pdf> = ArrayList()
    private lateinit var pdfadapter: PdfAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf)
        val tool = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(tool)
        Toast.makeText(this, "Please....Wait...", Toast.LENGTH_LONG).show()
        pdf_list.layoutManager = LinearLayoutManager(
            this
        )
        fetchData()
        pdfadapter = PdfAdapter(this)
        pdf_list.adapter = pdfadapter
    }

    private fun fetchData() {
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=60819377e6cc453a99f2a521ef8f0664"
        val jsonObjectRequest = object: JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener {
                val article= it.getJSONArray("articles")
                for (i in 0 until article.length()) {
                    val value = article.getJSONObject(i)
                    val data =
                        Pdf(
                            value.getString("url"),
                            value.getString("urlToImage"),
                            value.getString("title"),
                            value.getString("description")
                        )
                    list.add(data)
                }
                val list2 = ArrayList<Pdf>(list.size)
                val size =list.size -1
                for(i in size downTo 0)
                {
                    list2.add(list[i])
                }
                pdfadapter.updateItem(list2)
            },
            Response.ErrorListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val menuItem = menu!!.findItem(R.id.search_bar)
        if(menuItem != null) {
            val searchView = menuItem.actionView as SearchView
            searchView.queryHint = "Search Class"

            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText!!.isNotEmpty()) {
                        val list2 = ArrayList<Pdf>(list.size)
                        val size =list.size -1
                        for(i in size downTo 0)
                        {
                            list2.add(list[i])
                        }
                        list2.clear()
                        val search = newText.toLowerCase(Locale.getDefault())
                        list.forEach {
                            if(it.pdf_category.toLowerCase(Locale.getDefault()).contains(search)) {
                                list2.add(it)
                            }
                        }
                        pdfadapter.updateItem(list2)
                    }
                    else{
                        val list2 = ArrayList<Pdf>(list.size)
                        val size =list.size -1
                        for(i in size downTo 0)
                        {
                            list2.add(list[i])
                        }
                        pdfadapter.updateItem(list2)
                    }


                    return true
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }


    override fun onItemClicked(item: Pdf) {
        val news = item.pdfSrc
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(news))
    }

}

