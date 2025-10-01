package ie.setu.mobileappdevassignment.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import ie.setu.mobileappdevassignment.R

open class NavActivity : AppCompatActivity() {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val navView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navView?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_collections -> {
                    if (this !is CollectionsActivity) {
                        startActivity(Intent(this, CollectionsActivity::class.java))
                        finish()
                    }
                    true
                }
                R.id.nav_Sets -> {
                    if (this !is SetsActivity) {
                        startActivity(Intent(this, SetsActivity::class.java))
                        finish()
                    }
                    true
                }
                R.id.nav_Add -> {
                    if (this !is AddActivity) {
                        startActivity(Intent(this, AddActivity::class.java))
                        finish()
                    }
                    true
                }
                R.id.nav_map -> {
                    if (this !is MapActivity) {
                        startActivity(Intent(this, MapActivity::class.java))
                        finish()
                    }
                    true
                }
                R.id.nav_discover -> {
                    if (this !is DiscoverActivity) {
                        startActivity(Intent(this, DiscoverActivity::class.java))
                        finish()
                    }
                    true
                }
                else -> false
            }
        }
    }
}