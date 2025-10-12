package ie.setu.madassignemtv2.utilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.activities.CollectionsActivity
import ie.setu.madassignemtv2.activities.SetsActivity
import ie.setu.madassignemtv2.controllers.CollectionsController
import ie.setu.madassignemtv2.controllers.SetsController
import ie.setu.madassignemtv2.models.LegoCollection

import kotlin.jvm.java

object NavigationUtils {



    fun setupBottomNavigation(activity: Activity, navView: BottomNavigationView) {
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_collections -> {
                    if (activity !is CollectionsActivity) {
                        activity.startActivity(Intent(activity, CollectionsActivity::class.java))
                        activity.finish()
                    }
                    true
                }
                R.id.nav_Sets -> {
                    if (activity !is SetsActivity) {
                        activity.startActivity(Intent(activity, SetsActivity::class.java))
                        activity.finish()
                    }
                    true
                }
                else -> false
            }
        }
    }


}