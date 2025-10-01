package ie.setu.mobileappdevassignment.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import ie.setu.mobileappdevassignment.R
import ie.setu.mobileappdevassignment.controllers.AddController
import ie.setu.mobileappdevassignment.databinding.FragmentAddSetBinding
import ie.setu.mobileappdevassignment.models.LegoSet
import ie.setu.mobileappdevassignment.utilities.Utils

class AddSetFragment: Fragment(R.layout.fragment_add_set) {
    private var _binding: FragmentAddSetBinding? = null
    private val binding get() = _binding!!
    private lateinit var controller: AddController
    private lateinit var utils: Utils

    override fun onCreate(savedInstance: Bundle?){
        super.onCreate(savedInstance)
        utils = Utils()
        controller = AddController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddSetBinding.inflate(inflater, container, false)

        val dropdownItems = controller.listCollectionNames()
        Log.d("dropdown items", dropdownItems.toString())

        val spinner = binding.collectionSpinner

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            dropdownItems
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinner.adapter = adapter

        binding.addSetButton.setOnClickListener {
            val setName = binding.nameField.text.toString()
            val setNumber = binding.setNumberField.text.toString().toInt()
            val setPieceCount = binding.setNumberField.text.toString().toInt()
            val setAgeRange = binding.ageRangeField.text.toString().toInt()
            val setPrice = binding.priceField.text.toString().toFloat()
            val setImage = ""
            val setIsPublic = binding.isPublicSwitch.isChecked
            val collection = controller.getCollectionFromName(spinner.selectedItem.toString())

            val set = LegoSet(
                setName,
                setNumber,
                setPieceCount,
                setAgeRange,
                setPrice,
                setImage,
                setIsPublic
            )
            controller.addSet(set, collection)
            setAdded()
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAdded(){
        val responseText = getString(R.string.added_set)
        Toast.makeText(requireContext(), responseText, Toast.LENGTH_LONG).show()
        binding.nameField.setText("")
        binding.setNumberField.setText("")
        binding.ageRangeField.setText("")
        binding.priceField.setText("")
        binding.pieceCountField.setText("")
        binding.isPublicSwitch.isChecked = false
    }

}