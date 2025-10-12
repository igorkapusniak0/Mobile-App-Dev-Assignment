package ie.setu.madassignemtv2.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.adapters.SetsAdapter
import ie.setu.madassignemtv2.controllers.SetsController
import ie.setu.madassignemtv2.databinding.ActivitySetsListBinding
import ie.setu.madassignemtv2.main.MainApp
import ie.setu.madassignemtv2.models.LegoCollection
import ie.setu.madassignemtv2.models.LegoSet
import ie.setu.madassignemtv2.utilities.GlobalData

class SetsListActivity: AppCompatActivity() {
    lateinit var app: MainApp
    private var controller = SetsController(this)
    private var globalData = GlobalData
    private lateinit var binding: ActivitySetsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)



        app = application as MainApp

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_collections -> {
                    val launcherIntent = Intent(this, CollectionsListActivity::class.java)
                    getResult.launch(launcherIntent)
                    true
                }
                R.id.nav_discover -> {
                    val launcherIntent = Intent(this, DiscoverActivity::class.java)
                    getResult.launch(launcherIntent)
                    true
                }
                else -> false
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = SetsAdapter(getCollection(), binding.recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_collection, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, SetsActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
            registerForActivityResult(
                    ActivityResultContracts.StartActivityForResult()
            ) {
        if (it.resultCode == RESULT_OK) {
            (binding.recyclerView.adapter)?.
            notifyItemRangeChanged(0,getCollection().size)
        }
    }

    private fun getCollection() : MutableList<LegoSet>{
        var sets: MutableList<LegoSet>
        val collectionName = intent.getStringExtra("collection_name")
        sets = if (collectionName == null){
            globalData.loggedUserData.sets
        } else{
            controller.getCollectionFromName(collectionName).sets
        }

        return sets
    }


}
