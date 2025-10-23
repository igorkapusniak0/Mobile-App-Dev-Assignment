package ie.setu.madassignemtv2.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.controllers.SetsController
import ie.setu.madassignemtv2.databinding.ActivitySetBinding
import ie.setu.madassignemtv2.main.MainApp
import ie.setu.madassignemtv2.models.LegoSet
import ie.setu.madassignemtv2.utilities.LocaleHelper
import ie.setu.madassignemtv2.utilities.Utils

class SetActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySetBinding

    lateinit var app: MainApp
    private var controller = SetsController(this)
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

        val setName = intent.getStringExtra("set_name")
        val public = intent.getStringExtra("public")
        var set: LegoSet
        Log.d("set name", setName.toString())
        if (public == "public"){
            set = controller.getPublicSetFromName(setName.toString())
            Log.d("public set", set.toString())
        }
        else {
            set = controller.getSetFromName(setName.toString())
            Log.d("private set", set.toString())
        }

        binding = ActivitySetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        binding.nameText.text = set.name
        binding.numberText.text = set.setNumber.toString()
        binding.pieceCountText.text = set.pieceCount.toString()
        binding.ageText.text = set.age.toString()
        binding.priceText.text = set.price.toString()

        }
}