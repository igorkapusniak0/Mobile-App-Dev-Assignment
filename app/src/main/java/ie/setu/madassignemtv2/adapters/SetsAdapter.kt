package ie.setu.madassignemtv2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.madassignemtv2.controllers.CollectionsController
import ie.setu.madassignemtv2.controllers.SetsController
import ie.setu.madassignemtv2.databinding.CardSetBinding
import ie.setu.madassignemtv2.models.LegoSet

class SetsAdapter(private var sets: MutableList<LegoSet>, recyclerView: RecyclerView) : RecyclerView.Adapter<SetsAdapter.MainHolder>() {
    private val view = recyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardSetBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val set = sets[holder.adapterPosition]
        holder.bind(set, view)
    }

    override fun getItemCount(): Int = sets.size

    class MainHolder(private val binding: CardSetBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(set: LegoSet, recyclerView: RecyclerView) {
            val setsController = SetsController(binding.root.context)
            binding.titleText.text = set.name
            binding.idText.text = set.setNumber.toString()

            binding.imageButton.setOnClickListener {
                setsController.showBottomSheet(binding.root.context, set, recyclerView)
            }
        }


    }
}