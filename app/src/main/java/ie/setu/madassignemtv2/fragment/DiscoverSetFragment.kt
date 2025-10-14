package ie.setu.madassignemtv2.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.activities.EditSetActivity
import ie.setu.madassignemtv2.activities.SetsListActivity
import ie.setu.madassignemtv2.adapters.SetsAdapter
import ie.setu.madassignemtv2.controllers.DiscoverController
import ie.setu.madassignemtv2.databinding.FragmentSetsBinding
import ie.setu.madassignemtv2.models.LegoCollection
import ie.setu.madassignemtv2.models.LegoSet

class SetsFragment : Fragment(R.layout.fragment_sets) {
    private var _binding: FragmentSetsBinding? = null
    private val binding get() = _binding!!

    private lateinit var controller: DiscoverController
    private lateinit var setsAdapter: SetsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = DiscoverController(requireContext())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSetsBinding.bind(view)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val sets = controller.getAllPublicSets()
        Log.d("sts", sets.toString())
        setsAdapter = SetsAdapter(sets, binding.recyclerView,this::onCollectionSelected, this::onEditSetClicked)
        binding.recyclerView.adapter = setsAdapter
        setsAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCollectionSelected(set: LegoSet) {
        val intent = Intent(binding.root.context, SetsListActivity::class.java)
        intent.putExtra("set_name", set.name)
        startActivity(intent)
    }

    private val editResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun onEditSetClicked(set: LegoSet) {
        val intent = Intent(context, EditSetActivity::class.java)
        intent.putExtra("set_name", set.name)
        editResultLauncher.launch(intent)
    }
}
