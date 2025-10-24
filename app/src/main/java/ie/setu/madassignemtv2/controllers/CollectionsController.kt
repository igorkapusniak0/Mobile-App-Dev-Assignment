package ie.setu.madassignemtv2.controllers

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.activities.DiscoverActivity
import ie.setu.madassignemtv2.activities.EditCollectionActivity
import ie.setu.madassignemtv2.activities.EditSetActivity
import ie.setu.madassignemtv2.activities.SetsListActivity
import ie.setu.madassignemtv2.adapters.CollectionsAdapter
import ie.setu.madassignemtv2.adapters.SetsAdapter
import ie.setu.madassignemtv2.models.LegoCollection
import ie.setu.madassignemtv2.utilities.GlobalData
import ie.setu.madassignemtv2.utilities.Utils
import kotlin.collections.remove

class CollectionsController(context: Context) {
    private var globalData = GlobalData
    private val utils = Utils(context)

    fun getUserCollections(): MutableList<LegoCollection> {
        return globalData.loggedUserData.collections
    }

    fun addCollection(collection: LegoCollection) {
        globalData.loggedUserData.collections.add(collection.copy())
        utils.saveUsersToFile()

    }

    fun removeCollection(collection: LegoCollection){
        for(set in collection.sets){
            globalData.loggedUserData.sets.remove(set)
        }
        globalData.loggedUserData.collections.remove(collection)
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

    fun collectionNameExists(name: String): Boolean {
        return globalData.loggedUserData.collections.indexOfFirst { it.name == name } != -1
    }


    fun showBottomSheet(context: Context, collection: LegoCollection, recyclerView: RecyclerView, onEditClicked: (LegoCollection) -> Unit) {
        val bottomSheetDialog = BottomSheetDialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_menu, null)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()

        view.findViewById<TextView>(R.id.delete_option).setOnClickListener {
            removeCollection(collection)
            val updatedCollections = filterCollection("", getUserCollections())
            (recyclerView.adapter as? CollectionsAdapter)?.updateList(updatedCollections)
            val text = context.getString(R.string.collection_removed)
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            bottomSheetDialog.dismiss()
        }

        view.findViewById<TextView>(R.id.edit_option).setOnClickListener {
            onEditClicked(collection)
            bottomSheetDialog.dismiss()
        }

        view.findViewById<TextView>(R.id.view_option).setOnClickListener {
            val intent = Intent(context, SetsListActivity::class.java)
            intent.putExtra("collection_name", collection.name)
            context.startActivity(intent)
            recyclerView.adapter?.notifyDataSetChanged()
            bottomSheetDialog.dismiss()
        }
    }

    fun filterCollection(filter: String, collections: List<LegoCollection>) : MutableList<LegoCollection>{
        val filteredCollections = mutableListOf<LegoCollection>()
        if (filter.isEmpty()){
            filteredCollections.addAll(collections)
        }
        else{
            for (collection in collections){
                if (collection.toString().contains(filter, ignoreCase = true)){
                    filteredCollections.add(collection)
                }
            }
        }

        return filteredCollections
    }



}