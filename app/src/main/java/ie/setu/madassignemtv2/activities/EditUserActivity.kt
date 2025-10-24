package ie.setu.madassignemtv2.activities


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.controllers.LoginController
import ie.setu.madassignemtv2.databinding.ActivityEditUserBinding
import ie.setu.madassignemtv2.main.MainApp
import ie.setu.madassignemtv2.utilities.GlobalData
import ie.setu.madassignemtv2.utilities.LocaleHelper
import ie.setu.madassignemtv2.utilities.Utils

class EditUserActivity: AppCompatActivity() {

    lateinit var app: MainApp
    private var globalData = GlobalData
    private var utils = Utils(this)
    private lateinit var binding: ActivityEditUserBinding
    private lateinit var loginController: LoginController

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
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        loginController = LoginController(this)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp



        binding.updateUser.setOnClickListener {


            val newUserName = binding.nameField.text.toString()
            val newPassword = binding.passwordField.text.toString()
            val usernameTaken = loginController.userExists(newUserName)

            var pass = true

            if (newUserName.length <= 20 && newUserName.length >= 2 && !usernameTaken) {
                val index = globalData.usersData.indexOfFirst { it.name == globalData.loggedUserData.name }
                if (index != -1) {
                    globalData.usersData[index].name = newUserName
                    globalData.loggedUserData.name = newUserName
                }
            }
            else {
                pass = false
                binding.missing.text = getString(R.string.user_name_length)
            }

            if (newPassword.length <= 30 && newPassword.length >= 5) {
                val index = globalData.usersData.indexOfFirst { it.name == globalData.loggedUserData.name }
                if (index != -1) {
                    globalData.usersData[index].password = newPassword
                    globalData.loggedUserData.password = newPassword
                }
            }
            else {
                pass = false
                binding.missing.text = getString(R.string.user_password_length)
            }

            if (!usernameTaken && pass) {
                utils.saveUsersToFile()
                setResult(RESULT_OK)
                finish()
            }
        }
    }
}