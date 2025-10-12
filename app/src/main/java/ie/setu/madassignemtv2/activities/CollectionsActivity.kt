package ie.setu.madassignemtv2.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.controllers.CollectionsController
import ie.setu.madassignemtv2.databinding.ActivityCollectionsBinding
import ie.setu.madassignemtv2.main.MainApp
import ie.setu.madassignemtv2.models.LegoCollection
import ie.setu.madassignemtv2.utilities.GlobalData
import ie.setu.madassignemtv2.utilities.Utils

class CollectionsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCollectionsBinding
    private var utils = Utils(this)
    private var controller = CollectionsController(this)
    var collection = LegoCollection()
    lateinit var app: MainApp

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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

        binding.addCollectionButton.setOnClickListener {
            collection.name = binding.nameField.text.toString()
            collection.description = binding.descriptionField.text.toString()
            collection.creationDate = utils.getDate()
            collection.isPublic = binding.isPublicSwitch.isChecked
            if (collection.name.isNotEmpty() && collection.description.isNotEmpty()) {
                controller.addCollection(collection)
                Log.i("add Button Pressed", collection.toString())
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a Title and/or Description", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}