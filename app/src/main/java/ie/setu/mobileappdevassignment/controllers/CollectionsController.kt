package ie.setu.mobileappdevassignment.controllers

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import ie.setu.mobileappdevassignment.R
import ie.setu.mobileappdevassignment.utilities.GlobalData
import ie.setu.mobileappdevassignment.models.LegoCollection
import ie.setu.mobileappdevassignment.utilities.Utils


class CollectionsController(context: Context) {
    private var globalData = GlobalData
    private val utils = Utils(context)

    fun getUserCollections(): MutableList<LegoCollection>{
        var userCollections = mutableListOf<LegoCollection>()
        for (user in globalData.usersData){
            if (globalData.loggedUserData.name == user.name){
                userCollections = user.collections
            }
        }
        if (userCollections.isEmpty()){
            val collection = LegoCollection("test", "test", "test")
            userCollections.add(collection)
        }

        return userCollections
    }

    fun addCollectionView(context: Context, rootContainer: LinearLayout) {
        val inflater = LayoutInflater.from(context)
        val collections = getUserCollections()

        collections.add(LegoCollection("City Set", "Includes police station and vehicles.", "2023-09-12"))
        collections.add(LegoCollection("Star Wars Set", "A big set with many pieces.", "2023-08-01"))
        collections.add(LegoCollection("City Set", "Includes police station and vehicles.", "2023-09-12"))
        collections.add(LegoCollection("City Set", "Includes police station and vehicles.", "2023-09-12"))
        collections.add(LegoCollection("City Set", "Includes police station and vehicles.", "2023-09-12"))
        collections.add(LegoCollection("City Set", "Includes police station and vehicles.", "2023-09-12"))

        for (collection in collections) {
            val view = inflater.inflate(R.layout.collection_card, rootContainer, false)

            val titleText = view.findViewById<TextView>(R.id.title_text)
            val descriptionText = view.findViewById<TextView>(R.id.description_text)
            val dateText = view.findViewById<TextView>(R.id.date_text)

            titleText.text = collection.name
            descriptionText.text = collection.description
            dateText.text = collection.creationDate

            rootContainer.addView(view)
            Log.d("added", "collection")
        }
    }

}