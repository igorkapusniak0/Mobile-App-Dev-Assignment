package ie.setu.mobileappdevassignment.controllers

import android.util.Log
import ie.setu.mobileappdevassignment.models.LegoCollection
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

}