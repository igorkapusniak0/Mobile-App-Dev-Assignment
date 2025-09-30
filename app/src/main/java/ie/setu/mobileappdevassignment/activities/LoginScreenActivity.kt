package ie.setu.mobileappdevassignment.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ie.setu.mobileappdevassignment.controllers.LoginScreenController
import ie.setu.mobileappdevassignment.databinding.ActivityLoginScreenBinding

class LoginScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginScreenBinding
    private lateinit var controller: LoginScreenController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = LoginScreenController()

        controller.loadSaveFileToList(this)

        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.loginButton.setOnClickListener {
            val username = binding.usernameField.text.toString()
            val password = binding.passwordField.text.toString()
            if (controller.loginUser(username, password)){
                val intent = Intent(this@LoginScreenActivity, CollectionsActivity::class.java)
                startActivity(intent)
            }
            Log.d("Username" ,username)
            Log.d("Password" ,password)
        }

        binding.registerButton.setOnClickListener {
            val username = binding.usernameField.text.toString()
            val password = binding.passwordField.text.toString()
            if (controller.registerUser(this, username, password)){
                val intent = Intent(this@LoginScreenActivity, CollectionsActivity::class.java)
                startActivity(intent)
            }

            Log.d("Username" ,username)
            Log.d("Password" ,password)
        }


    }
}