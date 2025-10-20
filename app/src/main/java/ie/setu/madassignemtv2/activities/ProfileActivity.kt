package ie.setu.madassignemtv2.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import ie.setu.madassignemtv2.utilities.LocaleHelper
import ie.setu.madassignemtv2.utilities.Utils

class ProfileActivity: AppCompatActivity() {
    lateinit var app: MainApp
    private val globalData = GlobalData
    private lateinit var utils: Utils

    private lateinit var binding: ActivityProfileBinding
    private var isFirstLanguageSelection = true

    override fun attachBaseContext(newBase: Context) {
        val context = LocaleHelper.setLocale(newBase, "pl")
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        utils = Utils(this)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        app = application as MainApp

        val dropdownItems = listOf("Select Language", "eng", "pl")


        val spinner = binding.languageSpinner

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            dropdownItems
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinner.adapter = adapter

        binding.editUser.setOnClickListener {
            val launcherIntent = Intent(this, EditUserActivity::class.java)
            startActivity(launcherIntent)
        }

        binding.darkMode.setOnClickListener {
            utils.toggleDarkMode()
        }

        binding.signoutText.setOnClickListener {
            val launcherIntent = Intent(this, LoginActivity::class.java)
            utils.saveUsersToFile()
            startActivity(launcherIntent)
            true
        }

        binding.languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (isFirstLanguageSelection) {
                    isFirstLanguageSelection = false
                    return
                }

                val language = parent.getItemAtPosition(position).toString()
                if (language == "pl" || language == "eng") {
                    utils.setLanguage(language)
                    LocaleHelper.setLocale(this@ProfileActivity, language)

                    val intent = Intent(this@ProfileActivity, ProfileActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
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