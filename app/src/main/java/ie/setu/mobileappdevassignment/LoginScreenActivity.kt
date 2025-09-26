package ie.setu.mobileappdevassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ie.setu.mobileappdevassignment.databinding.ActivityLoginScreenBinding
import ie.setu.mobileappdevassignment.controllers.LoginScreenController
import android.util.Log

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
            controller.loginUser(username, password)
            Log.d("Username" ,username)
            Log.d("Password" ,password)
        }

        binding.registerButton.setOnClickListener {
            val username = binding.usernameField.text.toString()
            val password = binding.passwordField.text.toString()
            controller.registerUser(this, username, password)
            Log.d("Username" ,username)
            Log.d("Password" ,password)
        }


    }
}
