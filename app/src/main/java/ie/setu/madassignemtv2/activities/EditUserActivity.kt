package ie.setu.madassignemtv2.activities


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.databinding.ActivityEditUserBinding
import ie.setu.madassignemtv2.main.MainApp
import ie.setu.madassignemtv2.utilities.GlobalData

class EditUserActivity: AppCompatActivity() {

    lateinit var app: MainApp
    private var globalData = GlobalData
    private lateinit var binding: ActivityEditUserBinding

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
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        binding.updateUser.setOnClickListener {
            if(binding.nameField.text.toString().isNotEmpty()){
                globalData.loggedUserData.name = binding.nameField.text.toString()
            }
            if (binding.passwordField.text.toString().isNotEmpty()){
                globalData.loggedUserData.password = binding.passwordField.text.toString()
            }

            setResult(RESULT_OK)
            finish()

        }
    }
}