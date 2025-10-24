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

        val collectionName = intent.getStringExtra("collection_name")
        binding.addCollectionButton.text = getString(R.string.update_collection)
        var collection = LegoCollection()
        if(collectionName != null){
            collection = controller.getCollectionFromName(collectionName)
        }

        binding.nameField.hint = collection.name
        binding.descriptionField.hint = collection.description
        binding.isPublicSwitch.isChecked = collection.isPublic



        binding.addCollectionButton.setOnClickListener {

            val name = binding.nameField.text.toString()
            val description = binding.descriptionField.text.toString()
            val isPublic = binding.isPublicSwitch.isChecked

            if (name.isNotEmpty()){
                if ((name.length <= 20 && name.length >= 2) && !controller.collectionNameExists(name)) {
                    collection.name = name
                }
                else{
                    Snackbar.make(it,getString(R.string.collection_name_length), Snackbar.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            }

            if (description.isNotEmpty()){
                if (description.length <= 30 && description.length >= 5){
                    collection.description = description
                }
                else{
                    Snackbar.make(it,getString(R.string.collection_description_length), Snackbar.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            }

            collection.isPublic = isPublic

            utils.saveUsersToFile()

            setResult(RESULT_OK)
            finish()



        }
    }
}