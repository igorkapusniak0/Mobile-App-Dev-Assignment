package ie.setu.mobileappdevassignment.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ie.setu.mobileappdevassignment.R
import ie.setu.mobileappdevassignment.controllers.LoginController
import ie.setu.mobileappdevassignment.databinding.ActivityLoginScreenBinding
import ie.setu.mobileappdevassignment.utilities.Utils

class LoginScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginScreenBinding
    private lateinit var controller: LoginController
    private lateinit var utils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = LoginController()
        utils = Utils()

        utils.loadSaveFileToList(this, controller.saveFileName)

        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val username = binding.usernameField.text.toString()
            val password = binding.passwordField.text.toString()
            val loginResponse: Int = controller.loginUser(username, password)
            when (loginResponse) {
                1 -> {
                    val intent = Intent(this@LoginScreenActivity, CollectionsActivity::class.java)
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
            val registerResponse: Boolean = controller.registerUser(this, username, password)
            if (registerResponse){
                val intent = Intent(this@LoginScreenActivity, CollectionsActivity::class.java)
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