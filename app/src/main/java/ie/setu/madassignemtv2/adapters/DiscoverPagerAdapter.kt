package ie.setu.madassignemtv2.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ie.setu.madassignemtv2.fragment.SetsFragment
import ie.setu.madassignemtv2.fragments.CollectionsFragment

class DiscoverPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SetsFragment()
            1 -> CollectionsFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}
