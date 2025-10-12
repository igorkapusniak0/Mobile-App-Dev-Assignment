package ie.setu.madassignemtv2.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.controllers.LoginController
import ie.setu.madassignemtv2.databinding.ActivityLoginBinding
import ie.setu.madassignemtv2.main.MainApp
import ie.setu.madassignemtv2.utilities.Utils

class LoginActivity : AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding
    private var loginController = LoginController(this)
    lateinit var app: MainApp
    private lateinit var utils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        utils = Utils(this)

        utils.loadSaveFileToList()

        app = application as MainApp

        binding.loginButton.setOnClickListener {
            val username = binding.usernameField.text.toString()
            val password = binding.passwordField.text.toString()
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

        binding.registerButton.setOnClickListener {
            val username = binding.usernameField.text.toString()
            val password = binding.passwordField.text.toString()
            val registerResponse: Boolean = loginController.registerUser(this, username, password)
            if (registerResponse){
                val intent = Intent(this@LoginActivity, CollectionsActivity::class.java)
                startActivity(intent)
            }
            else{
                binding.errorMessageTextView.text = getString(R.string.user_exist)
            }

            Log.d("Username" ,username)
            Log.d("Password" ,password)
        }

    }
}