package ie.setu.mobileappdevassignment.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.LinearLayout

import ie.setu.mobileappdevassignment.R
import ie.setu.mobileappdevassignment.controllers.CollectionsController
import ie.setu.mobileappdevassignment.databinding.ActivityCollectionsBinding

class CollectionsActivity : NavActivity() {
    private lateinit var binding: ActivityCollectionsBinding
    private lateinit var controller: CollectionsController
    private lateinit var rootContainer: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollectionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        controller = CollectionsController()

        rootContainer = findViewById(R.id.rootContainer)

        controller.addCollectionView(this, rootContainer)
    }


}

