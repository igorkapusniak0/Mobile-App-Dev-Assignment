package ie.setu.madassignemtv2.activities

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

class EditSetActivity: AppCompatActivity() {

    lateinit var app: MainApp
    private var controller = SetsController(this)
    private lateinit var binding: ActivitySetsBinding

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

        binding.addSetButton.setOnClickListener {
            if(binding.nameField.text.toString().isNotEmpty()){
                set.name = binding.nameField.text.toString()
            }
            if (binding.setNumberField.text.toString().isNotEmpty()){
                set.setNumber = binding.setNumberField.text.toString().toInt()
            }
            if (binding.pieceCountField.text.toString().isNotEmpty()){
                set.pieceCount = binding.pieceCountField.text.toString().toInt()
            }
            if (binding.priceField.text.toString().isNotEmpty()){
                set.price = binding.priceField.text.toString().toFloat()
            }

            set.isPublic = binding.isPublicSwitch.isChecked

            if (set.collectionName != binding.collectionSpinner.selectedItem.toString()){
                val collection = controller.getCollectionFromName(binding.collectionSpinner.selectedItem.toString())
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