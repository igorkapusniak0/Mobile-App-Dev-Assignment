package ie.setu.madassignemtv2.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.activities.SetsListActivity
import ie.setu.madassignemtv2.adapters.CollectionsAdapter
import ie.setu.madassignemtv2.adapters.SetsAdapter
import ie.setu.madassignemtv2.controllers.DiscoverController
import ie.setu.madassignemtv2.databinding.FragmentCollectionsBinding
import ie.setu.madassignemtv2.databinding.FragmentSetsBinding
import ie.setu.madassignemtv2.models.LegoCollection

class CollectionsFragment : Fragment(R.layout.fragment_sets) {
    private var _binding: FragmentSetsBinding? = null
    private val binding get() = _binding!!

    private lateinit var controller: DiscoverController
    private lateinit var setsAdapter: CollectionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = DiscoverController(requireContext())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSetsBinding.bind(view)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val collections = controller.getAllPublicCollections()
        Log.d("sts", collections.toString())
        setsAdapter = CollectionsAdapter(collections, this::onCollectionSelected, binding.recyclerView)
        binding.recyclerView.adapter = setsAdapter
        setsAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCollectionSelected(collection: LegoCollection) {
        val intent = Intent(binding.root.context, SetsListActivity::class.java)
        intent.putExtra("collection_name", collection.name)
        startActivity(intent)
    }
}
