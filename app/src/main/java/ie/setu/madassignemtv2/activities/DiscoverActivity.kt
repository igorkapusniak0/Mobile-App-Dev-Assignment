package ie.setu.madassignemtv2.activities

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ie.setu.madassignemtv2.R

import ie.setu.madassignemtv2.adapters.DiscoverPagerAdapter
import ie.setu.madassignemtv2.databinding.ActivityCollectionsListBinding
import ie.setu.madassignemtv2.databinding.ActivityDiscoverBinding
import ie.setu.madassignemtv2.databinding.ActivitySetsListBinding
import ie.setu.madassignemtv2.main.MainApp
import ie.setu.madassignemtv2.models.LegoCollection
import ie.setu.madassignemtv2.utilities.GlobalData
import ie.setu.madassignemtv2.utilities.LocaleHelper
import ie.setu.madassignemtv2.utilities.Utils

class DiscoverActivity: AppCompatActivity() {
    lateinit var app: MainApp
    private val globalData = GlobalData
    private lateinit var binding: ActivityDiscoverBinding
    private lateinit var discoverPagerAdapter: DiscoverPagerAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private val utils = Utils(this)


    override fun attachBaseContext(newBase: Context) {
        val context = LocaleHelper.setLocale(newBase, utils.getLanguage())
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiscoverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = getString(R.string.discover)
        setSupportActionBar(binding.toolbar)

        if (!globalData.loggedUserData.darkMode){
            binding.toolbar.setTitleTextColor(getColor(R.color.black))
        }
        else{
            binding.toolbar.setTitleTextColor(getColor(R.color.white))
        }

        app = application as MainApp

        viewPager2 = binding.viewPager
        tabLayout = binding.tabLayout

        discoverPagerAdapter = DiscoverPagerAdapter(this)
        viewPager2.adapter = discoverPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.sets)
                1 -> tab.text = getString(R.string.collections)
            }
        }.attach()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_Sets -> {
                    val launcherIntent = Intent(this, SetsListActivity::class.java)
                    getResultSets.launch(launcherIntent)
                    true
                }
                R.id.nav_collections -> {
                    val launcherIntent = Intent(this, CollectionsListActivity::class.java)
                    getResultCollections.launch(launcherIntent)
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

    val getResultSets = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            var binding = ActivitySetsListBinding.inflate(layoutInflater)
            (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, globalData.loggedUserData.sets.size)
        }
    }

    val getResultCollections = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            var binding = ActivityCollectionsListBinding.inflate(layoutInflater)
            (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, globalData.loggedUserData.collections.size)
        }
    }

    private fun onCollectionSelected(collection: LegoCollection) {
        val intent = Intent(this, SetsListActivity::class.java)
        intent.putExtra("collection_name", collection.name)
        startActivity(intent)
    }
}
