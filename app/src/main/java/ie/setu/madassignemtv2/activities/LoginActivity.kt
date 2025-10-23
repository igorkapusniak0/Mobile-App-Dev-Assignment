package ie.setu.madassignemtv2.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.controllers.LoginController
import ie.setu.madassignemtv2.databinding.ActivityLoginBinding
import ie.setu.madassignemtv2.main.MainApp
import ie.setu.madassignemtv2.utilities.LocaleHelper
import ie.setu.madassignemtv2.utilities.Utils

class LoginActivity : AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding
    private var loginController = LoginController(this)
    lateinit var app: MainApp
    private val utils = Utils(this)


    override fun attachBaseContext(newBase: Context) {
        val context = LocaleHelper.setLocale(newBase, utils.getLanguage())
        super.attachBaseContext(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        utils.loadSaveFileToList()

        app = application as MainApp

        val dropdownItems = listOf(getString(R.string.select_language), "eng", "pl")


        val spinner = binding.languageSpinner

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            dropdownItems
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinner.adapter = adapter

        binding.languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val language = parent.getItemAtPosition(position).toString()
                if (language == "pl" || language == "eng") {
                    if (language != utils.getLanguage()) {
                        utils.setLanguage(language)
                        LocaleHelper.setLocale(this@LoginActivity, language)

                        val intent = Intent(this@LoginActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.loginButton.setOnClickListener {
            var pass = true
            val username = binding.usernameField.text.toString()
            val password = binding.passwordField.text.toString()

            if (username.length > 20 || username.length < 2){
                pass = false
                binding.errorMessageTextView.text = getString(R.string.user_name_length)
            }

            if (password.length > 30 || password.length < 5){
                pass = false
                binding.errorMessageTextView.text = getString(R.string.user_password_length)
            }


            if (pass){
                val loginResponse: Int = loginController.loginUser(username, password)
                when (loginResponse) {
                    1 -> {
                        val intent = Intent(this@LoginActivity, CollectionsListActivity::class.java)
                        startActivity(intent)
                    }
                    0 -> {
                        binding.errorMessageTextView.text = getString(R.string.user_not_exist)
                    }
                    2 -> {
                        binding.errorMessageTextView.text = getString(R.string.incorrect_password)
                    }
                }
                Log.d("Username" ,username)
                Log.d("Password" ,password)
            }

        }

        binding.registerButton.setOnClickListener {
            val username = binding.usernameField.text.toString()
            val password = binding.passwordField.text.toString()
            if (username.length <= 20 || (password.length <= 30 && password.length >= 5)){
                val registerResponse: Boolean = loginController.registerUser(this, username, password)
                if (registerResponse){
                    val intent = Intent(this@LoginActivity, CollectionsListActivity::class.java)
                    startActivity(intent)
                }
                else{
                    binding.errorMessageTextView.text = getString(R.string.user_exist)
                }

                Log.d("Username" ,username)
                Log.d("Password" ,password)
            }
            else{
                binding.errorMessageTextView.text = getString(R.string.user_exist)
            }

        }

    }
}