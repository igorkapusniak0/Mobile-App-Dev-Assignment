package ie.setu.madassignemtv2.activities;

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu;
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.controllers.SetsController;
import ie.setu.madassignemtv2.databinding.ActivitySetsBinding;
import ie.setu.madassignemtv2.main.MainApp;
import ie.setu.madassignemtv2.models.LegoSet;
import ie.setu.madassignemtv2.utilities.LocaleHelper
import ie.setu.madassignemtv2.utilities.Utils

class SetsActivity: AppCompatActivity() {
    
    private lateinit var binding: ActivitySetsBinding
    private var controller = SetsController(this)
    var set = LegoSet()
    lateinit var app: MainApp
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
        binding = ActivitySetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        val dropdownItems = controller.listCollectionNames()

        val spinner = binding.collectionSpinner

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            dropdownItems
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinner.adapter = adapter

        binding.addSetButton.setOnClickListener {
            var pass = true

            val name = binding.nameField.text.toString()
            val setNumber = binding.setNumberField.text.toString().toInt()
            val pieceCount = binding.pieceCountField.text.toString().toInt()
            val price = binding.priceField.text.toString().toFloat()
            val age = binding.ageRangeField.text.toString().toInt()
            val isPublic = binding.isPublicSwitch.isChecked

            if (name.length > 20 || name.length < 5){
                pass = false
                Snackbar.make(it,getString(R.string.set_name_length), Snackbar.LENGTH_LONG).show()
            }
            if (setNumber < 1 || setNumber > 9999999 ){
                pass = false
                Snackbar.make(it,getString(R.string.set_number_limit), Snackbar.LENGTH_LONG).show()
            }
            if (pieceCount < 50 || pieceCount > 999999){
                pass = false
                Snackbar.make(it,getString(R.string.set_piece_count_limit), Snackbar.LENGTH_LONG).show()
            }
            if (price < 1.0f ||  price > 99999.0f){
                pass = false
                Snackbar.make(it,getString(R.string.set_price_limit), Snackbar.LENGTH_LONG).show()
            }
            if (age < 3 || age > 18 ){
                pass = false
                Snackbar.make(it,getString(R.string.set_age_limit), Snackbar.LENGTH_LONG).show()
            }

            if (pass) {
                set.name = name
                set.setNumber = setNumber
                set.pieceCount = pieceCount
                set.price = price
                set.age = age
                set.isPublic =  isPublic
                set.collectionName = binding.collectionSpinner.selectedItem.toString()
                val collection = controller.getCollectionFromName(binding.collectionSpinner.selectedItem.toString())
                
                controller.addSet(set, collection)
                setResult(RESULT_OK)
                finish()
            }
        }
    }
}
