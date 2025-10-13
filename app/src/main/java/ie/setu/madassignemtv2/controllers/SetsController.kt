package ie.setu.madassignemtv2.controllers

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.models.LegoCollection
import ie.setu.madassignemtv2.models.LegoSet
import ie.setu.madassignemtv2.utilities.GlobalData
import ie.setu.madassignemtv2.utilities.Utils

class SetsController(context: Context) {
    private var globalData = GlobalData
    private val utils = Utils(context)

    fun getUserSets(): MutableList<LegoSet>{
        val userSets = mutableListOf<LegoSet>()
        for (collection in globalData.loggedUserData.collections){
            for (set in collection.sets){
                userSets.add(set)
            }
        }
        return userSets
    }

    fun addSet(set: LegoSet, collection: LegoCollection){
        collection.sets.add(set)
        globalData.loggedUserData.sets.add(set)
        utils.saveUsersToFile()
        Log.d("user sets", collection.sets.toString())

    }

    fun getCollectionFromName(name: String): LegoCollection {
        var collection = LegoCollection()
        for(col in globalData.loggedUserData.collections){
            if(col.name == name){
                collection = col
                break
            }
        }
        return collection
    }

    fun getSetFromName(name: String): LegoSet {
        var legoSet = LegoSet()
        for(set in globalData.loggedUserData.sets){
            if(set.name == name){
                legoSet = set
                break
            }
        }
        return legoSet
    }

    fun listCollectionNames(): List<String>{
        val collectionNames = mutableListOf<String>()
        for (collection in globalData.loggedUserData.collections) {
            collectionNames.add(collection.name)
        }
        return collectionNames
    }

    fun findCollectionFromSet(legoSet: LegoSet): LegoCollection{
        var legoCollection = LegoCollection()
        for (collection in globalData.loggedUserData.collections){
            for (set in collection.sets){
                if (set == legoSet){
                    legoCollection = collection
                    break
                }
            }
        }
        return legoCollection
    }

    fun removeSet(set: LegoSet){
        val collection = findCollectionFromSet(set)
        collection.sets.remove(set)
        globalData.loggedUserData.sets.remove(set)
        utils.saveUsersToFile()
    }

    fun showBottomSheet(context: Context, set: LegoSet, recyclerView: RecyclerView) {
        val bottomSheetDialog = BottomSheetDialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_menu, null)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()

        view.findViewById<TextView>(R.id.delete_option).setOnClickListener {
            removeSet(set)
            recyclerView.adapter?.notifyDataSetChanged()
            Toast.makeText(context, "Set Removed", Toast.LENGTH_SHORT).show()
            bottomSheetDialog.dismiss()
        }

        view.findViewById<TextView>(R.id.edit_option).setOnClickListener {
            Toast.makeText(context, "Editing Collection", Toast.LENGTH_SHORT).show()
            bottomSheetDialog.dismiss()
        }

        view.findViewById<TextView>(R.id.view_option).setOnClickListener {
            Toast.makeText(context, "Viewing Collection", Toast.LENGTH_SHORT).show()
            bottomSheetDialog.dismiss()
        }
    }

}