package ie.setu.madassignemtv2.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.adapters.CollectionsAdapter
import ie.setu.madassignemtv2.controllers.CollectionsController
import ie.setu.madassignemtv2.databinding.ActivityCollectionsListBinding
import ie.setu.madassignemtv2.databinding.ActivityProfileBinding
import ie.setu.madassignemtv2.main.MainApp
import ie.setu.madassignemtv2.models.LegoCollection
import ie.setu.madassignemtv2.utilities.GlobalData
import ie.setu.madassignemtv2.utilities.Utils

class ProfileActivity: AppCompatActivity() {
    lateinit var app: MainApp
    private val globalData = GlobalData
    private lateinit var utils: Utils

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        utils = Utils(this)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as MainApp

        binding.editUser.setOnClickListener {
            val launcherIntent = Intent(this, EditUserActivity::class.java)
            startActivity(launcherIntent)
        }

        binding.darkMode.setOnClickListener {
            utils.toggleDarkMode()
        }

        binding.signoutText.setOnClickListener {
            val launcherIntent = Intent(this, LoginActivity::class.java)
            startActivity(launcherIntent)
            true
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_Sets -> {
                    val launcherIntent = Intent(this, SetsListActivity::class.java)
                    startActivity(launcherIntent)
                    true
                }
                R.id.nav_discover -> {
                    val launcherIntent = Intent(this, DiscoverActivity::class.java)
                    startActivity(launcherIntent)
                    true
                }
                R.id.nav_collections -> {
                    val launcherIntent = Intent(this, CollectionsListActivity::class.java)
                    startActivity(launcherIntent)
                    true
                }
                R.id.nav_settings-> {
                    val launcherIntent = Intent(this, ProfileActivity::class.java)
                    startActivity(launcherIntent)
                    true
                }
                else -> false
            }
        }
    }

}