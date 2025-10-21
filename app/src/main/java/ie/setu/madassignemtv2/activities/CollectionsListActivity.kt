package ie.setu.madassignemtv2.activities

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.adapters.CollectionsAdapter
import ie.setu.madassignemtv2.controllers.CollectionsController
import ie.setu.madassignemtv2.databinding.ActivityCollectionsListBinding
import ie.setu.madassignemtv2.databinding.ActivitySetsListBinding
import ie.setu.madassignemtv2.main.MainApp
import ie.setu.madassignemtv2.models.LegoCollection
import ie.setu.madassignemtv2.utilities.GlobalData
import ie.setu.madassignemtv2.utilities.LocaleHelper
import ie.setu.madassignemtv2.utilities.Utils

class CollectionsListActivity: AppCompatActivity() {
    lateinit var app: MainApp
    private val globalData = GlobalData
    private lateinit var binding: ActivityCollectionsListBinding
    private lateinit var collections: MutableList<LegoCollection>
    private lateinit var controller: CollectionsController
    private val utils = Utils(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectionsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = getString(R.string.collections)
        controller = CollectionsController(this)
        setSupportActionBar(binding.toolbar)
        collections = mutableListOf()
        collections.addAll(globalData.loggedUserData.collections)

        app = application as MainApp

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
                R.id.nav_settings-> {
                    val launcherIntent = Intent(this, ProfileActivity::class.java)
                    startActivity(launcherIntent)
                    true
                }
                else -> false
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = CollectionsAdapter(collections, this::onCollectionSelected, binding.recyclerView, this::onEditCollectionClicked)
    }

    override fun attachBaseContext(newBase: Context) {
        val context = LocaleHelper.setLocale(newBase, utils.getLanguage())
        super.attachBaseContext(context)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_collection, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val input = query ?: ""
                Log.d("SearchInput", "Submitted: $input")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val input = newText ?: ""
                Log.d("SearchInput", "Changed: $input")
                collections = controller.filterCollection(input, globalData.loggedUserData.collections)
                Log.d("filter col", collections.toString())
                (binding.recyclerView.adapter as? CollectionsAdapter)?.updateList(collections)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, CollectionsActivity::class.java)
                getResult.launch(launcherIntent)
            }
            R.id.set_sort_name_asc -> {
                collections.sortBy { it.name }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            R.id.collection_sort_name_desc -> {
                collections.sortByDescending { it.name }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            R.id.collection_sort_date_asc -> {
                collections.sortBy { it.creationDate }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            R.id.collection_sort_date_desc -> {
                collections.sortByDescending { it.creationDate }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            R.id.collection_sort_sets_num_asc -> {
                collections.sortBy { it.numberOfSets() }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            R.id.collection_sort_sets_num_desc -> {
                collections.sortByDescending { it.numberOfSets() }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
        }
        return super.onOptionsItemSelected(item)
    }

     val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,globalData.loggedUserData.collections.size)
            }
    }

    private fun onCollectionSelected(collection: LegoCollection) {
        val intent = Intent(this, SetsListActivity::class.java)
        intent.putExtra("collection_name", collection.name)
        startActivity(intent)
    }

    private val editResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun onEditCollectionClicked(collection: LegoCollection) {
        val intent = Intent(this, EditCollectionActivity::class.java)
        intent.putExtra("collection_name", collection.name)
        editResultLauncher.launch(intent)
    }

}
