package ie.setu.madassignemtv2.activities

import android.content.Context
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
import ie.setu.madassignemtv2.utilities.LocaleHelper
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
                setResult(RESULT_CANCELED)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun attachBaseContext(newBase: Context) {
        val context = LocaleHelper.setLocale(newBase, utils.getLanguage())
        super.attachBaseContext(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        binding.addCollectionButton.setOnClickListener {
            var pass = true
            val name = binding.nameField.text.toString()
            val description = binding.descriptionField.text.toString()
            val creationDate = utils.getDate()
            val isPublic = binding.isPublicSwitch.isChecked

            if (name.length > 20 || name.length < 2){
                pass = false
                Snackbar.make(it,getString(R.string.collection_name_length), Snackbar.LENGTH_LONG).show()
            }
            if (description.length > 30 || description.length < 5){
                pass = false
                Snackbar.make(it,getString(R.string.collection_description_length), Snackbar.LENGTH_LONG).show()
            }

//            if (intent.hasExtra("set_name")){
//                get
//            }


            if (pass) {
                collection.name = name
                collection.description = description
                collection.creationDate = creationDate
                collection.isPublic = isPublic
                controller.addCollection(collection)
                setResult(RESULT_OK)
                finish()
            }

        }
    }
}