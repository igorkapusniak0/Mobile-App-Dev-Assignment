package ie.setu.madassignemtv2.controllers

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.activities.EditSetActivity
import ie.setu.madassignemtv2.activities.SetActivity
import ie.setu.madassignemtv2.activities.SetsListActivity
import ie.setu.madassignemtv2.adapters.SetsAdapter
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

    }

    fun moveSet(set: LegoSet, newCollectionName: String, formerCollectionName: String) {
        val newCollection = globalData.loggedUserData.collections.indexOfFirst { it.name == newCollectionName }
        val formerCollection = globalData.loggedUserData.collections.indexOfFirst { it.name == formerCollectionName }
        set.collectionName = newCollectionName
        globalData.loggedUserData.collections[formerCollection].sets.remove(set)
        globalData.loggedUserData.collections[newCollection].sets.add(set)

        utils.saveUsersToFile()
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

    fun getPublicCollectionFromName(name: String): LegoCollection {
        var collection = LegoCollection()
        for(user in globalData.usersData){
            for (col in user.collections){
                if(col.name == name){
                    collection = col
                    break
                }
            }
        }
        return collection
    }

    fun setNameExists(name: String): Boolean {
        return globalData.loggedUserData.sets.indexOfFirst { it.name == name } != -1
    }

    fun setIDExists(id: Int): Boolean {
        return globalData.loggedUserData.sets.indexOfFirst { it.setNumber == id } != -1
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

    fun getPublicSetFromName(name: String): LegoSet {
        var legoSet = LegoSet()
        for (user in globalData.usersData){
            for(set in user.sets){
                if(set.name == name){
                    legoSet = set
                    break
                }
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

    fun showBottomSheet(context: Context, set: LegoSet, recyclerView: RecyclerView, onEditClicked: (LegoSet) -> Unit) {
        val bottomSheetDialog = BottomSheetDialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_menu, null)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()

        view.findViewById<TextView>(R.id.delete_option).setOnClickListener {
            removeSet(set)
            val updatedSets = filterSets("", getUserSets())
            (recyclerView.adapter as? SetsAdapter)?.updateList(updatedSets)
            val text = context.getString(R.string.set_removed)

            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            bottomSheetDialog.dismiss()
        }

        view.findViewById<TextView>(R.id.edit_option).setOnClickListener {
            onEditClicked(set)
            bottomSheetDialog.dismiss()
        }

        view.findViewById<TextView>(R.id.view_option).setOnClickListener {
            val intent = Intent(context, SetActivity::class.java)
            intent.putExtra("set_name", set.name)
            context.startActivity(intent)
            recyclerView.adapter?.notifyDataSetChanged()
            bottomSheetDialog.dismiss()
        }
    }

    fun filterSets(filter: String, sets: List<LegoSet>) : MutableList<LegoSet>{
        val filteredSets = mutableListOf<LegoSet>()
        if (filter.isEmpty()){
            filteredSets.addAll(sets)
        }
        else{
            for (set in sets){
                if (set.toString().contains(filter, ignoreCase = true)){
                    filteredSets.add(set)
                }
            }
        }

        return filteredSets
    }

}