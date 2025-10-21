package ie.setu.madassignemtv2.activities

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.controllers.CollectionsController
import ie.setu.madassignemtv2.controllers.SetsController
import ie.setu.madassignemtv2.databinding.ActivityCollectionsBinding
import ie.setu.madassignemtv2.databinding.ActivitySetsBinding
import ie.setu.madassignemtv2.main.MainApp
import ie.setu.madassignemtv2.models.LegoCollection
import ie.setu.madassignemtv2.models.LegoSet
import ie.setu.madassignemtv2.utilities.LocaleHelper
import ie.setu.madassignemtv2.utilities.Utils

class EditCollectionActivity: AppCompatActivity() {
    lateinit var app: MainApp
    private var controller = CollectionsController(this)
    private lateinit var binding: ActivityCollectionsBinding
    private val utils = Utils(this)


    override fun attachBaseContext(newBase: Context) {
        val context = LocaleHelper.setLocale(newBase, utils.getLanguage())
        super.attachBaseContext(context)
    }


    override fun onCreateOptionsMenu(menu:Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cancel_collection, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                Log.i("Cancel Button Pressed","")
                setResult(RESULT_CANCELED)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        Log.i("Placemark Activity started...","")

        val collectionName = intent.getStringExtra("collection_name")

        var collection = LegoCollection()
        if(collectionName != null){
            collection = controller.getCollectionFromName(collectionName)
        }

        binding.nameField.hint = collection.name
        binding.descriptionField.hint = collection.description
        binding.isPublicSwitch.isChecked = collection.isPublic

        binding.addCollectionButton.setOnClickListener {
            if (binding.nameField.text.toString().isNotEmpty()) {
                collection.name = binding.nameField.text.toString()
            }
            if (binding.descriptionField.text.toString().isNotEmpty()){
                collection.description = binding.descriptionField.text.toString()
            }
            collection.isPublic = binding.isPublicSwitch.isChecked




            if (collection.name.isNotEmpty() && collection.description.isNotEmpty()) {
                Log.i("Edit Button Pressed", collection.toString())
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,getString(R.string.missing_collection_info), Snackbar.LENGTH_LONG).show()
            }
        }
    }
}