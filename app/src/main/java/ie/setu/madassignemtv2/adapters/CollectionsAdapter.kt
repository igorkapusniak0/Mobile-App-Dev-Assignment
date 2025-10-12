package ie.setu.madassignemtv2.adapters

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import ie.setu.madassignemtv2.activities.CollectionsListActivity
import ie.setu.madassignemtv2.activities.SetsListActivity
import ie.setu.madassignemtv2.controllers.CollectionsController
import ie.setu.madassignemtv2.databinding.CardCollectionBinding
import ie.setu.madassignemtv2.models.LegoCollection
import kotlin.math.max

class CollectionsAdapter(private var collections: MutableList<LegoCollection>, private val onCollectionClick: (LegoCollection) -> Unit, recyclerView: RecyclerView) : RecyclerView.Adapter<CollectionsAdapter.MainHolder>() {
    private val view = recyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val collection = collections[holder.adapterPosition]
        holder.bind(collection, onCollectionClick, view)
    }

    override fun getItemCount(): Int = collections.size

    class MainHolder(private val binding: CardCollectionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(collection: LegoCollection, onClick: (LegoCollection) -> Unit, recyclerView: RecyclerView) {
            val collectionsController = CollectionsController(binding.root.context)

            binding.titleText.text = collection.name
            binding.descriptionText.text = collection.description
            binding.dateText.text = collection.creationDate
            binding.numberOfSetsText.text = collection.numberOfSets().toString()

            binding.root.setOnClickListener {
                onClick(collection)
            }
            binding.imageButton.setOnClickListener {
                collectionsController.showBottomSheet(binding.root.context, collection, recyclerView)
            }

        }

    }
}