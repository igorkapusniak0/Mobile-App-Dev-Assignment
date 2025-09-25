package ie.setu.mobileappdevassignment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ie.setu.mobileappdevassignment.databinding.ActivityLoginScreenBinding

class LoginScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginButton.setOnClickListener {
            val username = binding.usernameField.text.toString()
            val password = binding.passwordField.text.toString()

            Log.d("Username" ,username  )
            Log.d("Password" ,password  )
        }

        binding.registerButton.setOnClickListener {
            val username = binding.usernameField.text.toString()
            val password = binding.passwordField.text.toString()

            Log.d("Username" ,username  )
            Log.d("Password" ,password  )
        }


    }
}
