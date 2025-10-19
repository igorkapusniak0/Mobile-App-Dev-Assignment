package ie.setu.madassignemtv2.activities;

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

class SetsActivity: AppCompatActivity() {
    
    private lateinit var binding: ActivitySetsBinding
    private var controller = SetsController(this)
    var set = LegoSet()
    lateinit var app: MainApp

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

        binding.addSetButton.setOnClickListener {
            set.name = binding.nameField.text.toString()
            set.setNumber = binding.setNumberField.text.toString().toInt()
            set.pieceCount = binding.pieceCountField.text.toString().toInt()
            set.price = binding.priceField.text.toString().toFloat()
            set.ageRange = binding.ageRangeField.toString().toInt()
            set.isPublic = binding.isPublicSwitch.isChecked
            set.collectionName = binding.collectionSpinner.selectedItem.toString()
            val collection = controller.getCollectionFromName(binding.collectionSpinner.selectedItem.toString())

            if (set.name.isNotEmpty() && set.setNumber != 0 && set.pieceCount != 0 && set.price != 0.0f) {
                controller.addSet(set, collection)
                Log.i("add Button Pressed", set.toString())
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a Title and/or Description", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}
