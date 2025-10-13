package ie.setu.madassignemtv2.controllers

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.activities.DiscoverActivity
import ie.setu.madassignemtv2.activities.SetsListActivity
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

        for (i in globalData.loggedUserData.collections.indices) {
            Log.i("Placemark[$i]", globalData.loggedUserData.collections[i].toString())

        }
    }

    fun removeCollection(collection: LegoCollection){
        for(set in collection.sets){
            globalData.loggedUserData.sets.remove(set)
        }
        globalData.loggedUserData.collections.remove(collection)
        utils.saveUsersToFile()
    }



    fun showBottomSheet(context: Context, collection: LegoCollection, recyclerView: RecyclerView) {
        val bottomSheetDialog = BottomSheetDialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_menu, null)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()

        view.findViewById<TextView>(R.id.delete_option).setOnClickListener {
            removeCollection(collection)
            Toast.makeText(context, "Collection Removed", Toast.LENGTH_SHORT).show()
            recyclerView.adapter?.notifyDataSetChanged()
            bottomSheetDialog.dismiss()
        }

        view.findViewById<TextView>(R.id.edit_option).setOnClickListener {
            Toast.makeText(context, "Editing Collection", Toast.LENGTH_SHORT).show()
            bottomSheetDialog.dismiss()
        }

        view.findViewById<TextView>(R.id.view_option).setOnClickListener {
            Toast.makeText(context, "Viewing Collection", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, SetsListActivity::class.java)
            intent.putExtra("collection_name", collection.name)
            context.startActivity(intent)
            bottomSheetDialog.dismiss()
        }
    }

}