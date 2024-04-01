package com.example.starts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.support.v4.view.MenuItemCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.starts.adapter.StarAdapter
import com.example.starts.beans.Star
import com.example.starts.services.StarService

class MainActivity : AppCompatActivity() {
    private var stars: List<Star>? = null
    private var recyclerView: RecyclerView? = null
    private var starAdapter: StarAdapter? = null
    private var service: StarService? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stars = ArrayList()
        service = StarService.instance
        init()
        recyclerView = findViewById(R.id.recycle_view)
        starAdapter = StarAdapter(this, service!!.findAll())
        if (recyclerView != null) {
            recyclerView!!.adapter = starAdapter
            recyclerView!!.layoutManager = LinearLayoutManager(this)
        }
    }

    fun init() {
        service!!.create(
            Star("Blake Lively", "http://www.stars-photos.com/image.php?id=3278", 3F)
        )
        service!!.create(
            Star("Jennifer Lawrence", "http://www.stars-photos.com/image.php?id=4593", 5F)
        )
//Ajouter d’autres starts
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu.findItem(R.id.app_bar_search)
        val searchView = MenuItemCompat.getActionView(menuItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if (starAdapter != null) {
                    starAdapter!!.filter.filter(newText)
                }
                return true
            }
        })
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share) {
            val txt = "Stars"
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Stars")
                .setText(txt)
                .startChooser()
        }
        return super.onOptionsItemSelected(item)
    }
}