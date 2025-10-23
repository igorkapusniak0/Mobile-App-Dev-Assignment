package ie.setu.madassignemtv2.activities

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
import ie.setu.madassignemtv2.adapters.SetsAdapter
import ie.setu.madassignemtv2.controllers.SetsController
import ie.setu.madassignemtv2.databinding.ActivitySetsListBinding
import ie.setu.madassignemtv2.main.MainApp
import ie.setu.madassignemtv2.models.LegoCollection
import ie.setu.madassignemtv2.models.LegoSet
import ie.setu.madassignemtv2.utilities.GlobalData
import ie.setu.madassignemtv2.utilities.LocaleHelper
import ie.setu.madassignemtv2.utilities.Utils

class SetsListActivity: AppCompatActivity() {
    lateinit var app: MainApp
    private var controller = SetsController(this)
    private var globalData = GlobalData
    private lateinit var sets: MutableList<LegoSet>
    private lateinit var binding: ActivitySetsListBinding

    private val utils = Utils(this)

    override fun attachBaseContext(newBase: Context) {
        val context = LocaleHelper.setLocale(newBase, utils.getLanguage())
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetsListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = getString(R.string.sets)
        setSupportActionBar(binding.toolbar)
        sets = mutableListOf()
        sets = getCollection()

        app = application as MainApp

        binding.bottomNavigation.selectedItemId = R.id.nav_Sets
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
        binding.recyclerView.adapter = SetsAdapter(sets, binding.recyclerView, this::onSetSelected , this::onEditSetClicked)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_sets, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        if (!globalData.loggedUserData.darkMode){
            binding.toolbar.setTitleTextColor(getColor(R.color.black))
        }
        else{
            binding.toolbar.setTitleTextColor(getColor(R.color.white))
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val input = query ?: ""
                Log.d("SearchInput", "Submitted: $input")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val input = newText ?: ""
                Log.d("SearchInput", "Changed: $input")
                sets = controller.filterSets(input, getCollection())
                Log.d("filter col", sets.toString())
                (binding.recyclerView.adapter as? SetsAdapter)?.updateList(sets)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, SetsActivity::class.java)
                getResult.launch(launcherIntent)
            }
            R.id.set_sort_name_asc -> {
                sets.sortBy { it.name }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            R.id.set_sort_name_desc -> {
                sets.sortByDescending { it.name }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            R.id.set_sort_number_asc -> {
                sets.sortBy { it.setNumber }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            R.id.set_sort_number_desc -> {
                sets.sortByDescending { it.setNumber }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            R.id.set_sort_pieceCount_asc -> {
                sets.sortBy { it.pieceCount }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            R.id.set_sort_pieceCount_desc -> {
                sets.sortByDescending { it.pieceCount }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            R.id.set_sort_age_asc -> {
                sets.sortBy { it.age }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            R.id.set_sort_age_desc -> {
                sets.sortByDescending { it.age }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            R.id.set_sort_price_asc -> {
                sets.sortBy { it.price }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
            R.id.set_sort_price_desc -> {
                sets.sortByDescending { it.price }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            (binding.recyclerView.adapter as SetsAdapter).updateList(getCollection())
        }
    }

    private fun getCollection() : MutableList<LegoSet>{
        val sets= mutableListOf<LegoSet>()
        val collectionName = intent.getStringExtra("collection_name")
        val public = intent.getStringExtra("public")
        Log.d("Is Public", public.toString())
        if (collectionName == null){
            sets.addAll(globalData.loggedUserData.sets)
        }
        else if (public == "public"){
            sets.addAll(controller.getPublicCollectionFromName(collectionName).sets)
        }
        else{
            sets.addAll(controller.getCollectionFromName(collectionName).sets)
        }

        return sets
    }

    private fun onSetSelected(set: LegoSet) {
        val intent = Intent(this, SetActivity::class.java)
        intent.putExtra("set_name", set.name)
        startActivity(intent)
    }

    private val editResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun onEditSetClicked(set: LegoSet) {
        val intent = Intent(this, EditSetActivity::class.java)
        intent.putExtra("set_name", set.name)
        editResultLauncher.launch(intent)
    }

}
