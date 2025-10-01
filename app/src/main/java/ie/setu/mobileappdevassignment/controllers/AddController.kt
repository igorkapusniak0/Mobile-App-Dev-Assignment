package ie.setu.mobileappdevassignment.controllers

import android.util.Log
import ie.setu.mobileappdevassignment.models.LegoCollection
import ie.setu.mobileappdevassignment.models.LegoSet
import ie.setu.mobileappdevassignment.utilities.GlobalData
import ie.setu.mobileappdevassignment.utilities.Utils


class AddController {
    var globalData = GlobalData
    private lateinit var utils: Utils
    fun addCollection(collection: LegoCollection){
        globalData.loggedUserData.collections.add(collection)
        Log.d("userData", globalData.loggedUserData.toString())
        Log.d("userData", globalData.usersData.toString())
    }

    fun addSet(set: LegoSet, collection: LegoCollection){
        collection.sets.add(set)
        Log.d("user sets", collection.sets.toString())
    }

    fun getCollectionFromName(name: String): LegoCollection{
        var collection = LegoCollection()
        for(col in globalData.loggedUserData.collections){
            if(col.name == name){
                collection = col
                break
            }
        }
        return collection
    }

    fun listCollectionNames(): List<String>{
        var collectionNames = mutableListOf<String>()
        for (collection in globalData.loggedUserData.collections) {
            collectionNames.add(collection.name)
        }
        return collectionNames
    }


}