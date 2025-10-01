package ie.setu.mobileappdevassignment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ie.setu.mobileappdevassignment.R
import ie.setu.mobileappdevassignment.controllers.AddController
import ie.setu.mobileappdevassignment.databinding.FragmentAddCollectionBinding
import ie.setu.mobileappdevassignment.models.LegoCollection
import ie.setu.mobileappdevassignment.utilities.Utils
class AddCollectionFragment : Fragment(R.layout.fragment_add_collection) {
    private var _binding: FragmentAddCollectionBinding? = null
    private val binding get() = _binding!!

    private lateinit var controller: AddController
    private lateinit var utils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        utils = Utils(requireContext())
        controller = AddController(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCollectionBinding.inflate(inflater, container, false)

        binding.addCollectionButton.setOnClickListener {
            val collectionName = binding.nameField.text.toString()
            val collectionDescription = binding.descriptionField.text.toString()
            val collectionIsPublic = binding.isPublicSwitch.isChecked
            val date = utils.getDate()

            val collection = LegoCollection(
                collectionName,
                collectionDescription,
                date,
                "",
                isPublic = collectionIsPublic
            )

            controller.addCollection(collection)
            collectionAdded()


        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun collectionAdded(){
        val responseText = getString(R.string.added_collection)
        Toast.makeText(requireContext(), responseText, Toast.LENGTH_LONG).show()
        binding.nameField.setText("")
        binding.descriptionField.setText("")
        binding.isPublicSwitch.isChecked = false
    }
}
