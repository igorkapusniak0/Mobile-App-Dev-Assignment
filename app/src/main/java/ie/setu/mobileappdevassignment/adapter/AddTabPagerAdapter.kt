package ie.setu.mobileappdevassignment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ie.setu.mobileappdevassignment.fragment.AddCollectionFragment
import ie.setu.mobileappdevassignment.fragment.AddSetFragment

class AddTabPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AddCollectionFragment()
            1 -> AddSetFragment()
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }
}
