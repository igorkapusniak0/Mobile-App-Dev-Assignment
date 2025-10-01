package ie.setu.mobileappdevassignment.models

import android.media.Image
import kotlinx.serialization.Serializable

@Serializable
data class LegoCollection(
    var name : String = "",
    var description : String = "",
    var creationDate : String = "",
    var imagePath: String = "",
    var sets: MutableList<LegoSet> = mutableListOf(),
    var isPublic: Boolean = false
) {


    fun numberOfSets() : Int {
        val numberOfSets : Int = sets.size
        return numberOfSets
    }

    fun minimumAge() : Int{
        var minAge : Int = 0
        for (set in sets){
            if (set.ageRange > minAge) {
                minAge = set.ageRange
            }
        }
        return minAge
    }

    override fun toString(): String {
        return "Name: " + name +
                "Description: " + description +
                "Creation Date: " + creationDate +
                "Sets: " + sets
    }

}
