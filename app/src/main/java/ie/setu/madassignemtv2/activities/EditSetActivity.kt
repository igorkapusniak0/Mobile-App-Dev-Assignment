package ie.setu.madassignemtv2.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.controllers.SetsController
import ie.setu.madassignemtv2.databinding.ActivitySetBinding
import ie.setu.madassignemtv2.databinding.ActivitySetsBinding
import ie.setu.madassignemtv2.databinding.ActivitySetsListBinding
import ie.setu.madassignemtv2.main.MainApp
import ie.setu.madassignemtv2.models.LegoCollection
import ie.setu.madassignemtv2.models.LegoSet
import ie.setu.madassignemtv2.utilities.GlobalData
import ie.setu.madassignemtv2.utilities.LocaleHelper
import ie.setu.madassignemtv2.utilities.Utils

class EditSetActivity: AppCompatActivity() {

    lateinit var app: MainApp
    private var controller = SetsController(this)
    private lateinit var binding: ActivitySetsBinding

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
        binding = ActivitySetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        Log.i("Placemark Activity started...","")

        val dropdownItems = controller.listCollectionNames()
        Log.d("dropdown items", dropdownItems.toString())

        val spinner = binding.collectionSpinner

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            dropdownItems
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinner.adapter = adapter

        val setName = intent.getStringExtra("set_name")

        var set = LegoSet()
        if(setName != null){
            set = controller.getSetFromName(setName)
        }

        val formerCollection = controller.getCollectionFromName(set.collectionName)

        binding.nameField.hint = set.name
        binding.setNumberField.hint = set.setNumber.toString()
        binding.pieceCountField.hint = set.pieceCount.toString()
        binding.priceField.hint = set.price.toString()
        binding.isPublicSwitch.isChecked = set.isPublic
        binding.collectionSpinner.setSelection(collectionIndex(set.collectionName,dropdownItems))
        Log.d("index of spinner :", collectionIndex(set.collectionName,dropdownItems).toString())
        Log.d("collection Name", set.collectionName)

        val name = binding.nameField.text.toString()
        val setNumber = binding.setNumberField.text.toString().toInt()
        val pieceCount = binding.pieceCountField.text.toString().toInt()
        val price = binding.priceField.text.toString().toFloat()
        val age = binding.ageRangeField.text.toString().toInt()
        val isPublic = binding.isPublicSwitch.isChecked
        val collectionName = binding.collectionSpinner.selectedItem.toString()

        binding.addSetButton.setOnClickListener {
            if((name.length <= 20 && name.length >= 5) && !controller.setNameExists(name)){
                set.name = name
            }
            else{
                Snackbar.make(it,getString(R.string.set_name_length), Snackbar.LENGTH_LONG).show()
            }
            if ((setNumber >= 1 && setNumber <= 9999999) && !controller.setIDExists(setNumber)){
                set.setNumber = setNumber
            }
            else{
                Snackbar.make(it,getString(R.string.set_number_limit), Snackbar.LENGTH_LONG).show()
            }
            if (pieceCount >= 50 && pieceCount <= 999999){
                set.pieceCount = pieceCount
            }
            else{
                Snackbar.make(it,getString(R.string.set_piece_count_limit), Snackbar.LENGTH_LONG).show()
            }
            if (price >= 1 && price <= 99999){
                set.price = price
            }
            else{
                Snackbar.make(it,getString(R.string.set_price_limit), Snackbar.LENGTH_LONG).show()
            }
            if (age >= 3 && age <= 18){
                set.price = price
            }
            else{
                Snackbar.make(it,getString(R.string.set_age_limit), Snackbar.LENGTH_LONG).show()
            }

            set.isPublic = isPublic

            if (set.collectionName != collectionName){
                val collection = controller.getCollectionFromName(collectionName)
                controller.moveSet(set,collection,formerCollection)
            }

            Log.i("Edit Button Pressed", set.toString())
            setResult(RESULT_OK)
            finish()

        }
    }

    private fun collectionIndex(collectionName: String, collections: List<String>): Int{
        var index = 0
        for(collection in collections){
            if (collectionName != collection){
                index++
            }
            else{
                break
            }
        }
        return index
    }
}