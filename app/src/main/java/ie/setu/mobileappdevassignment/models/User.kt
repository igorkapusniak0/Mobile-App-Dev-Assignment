package ie.setu.mobileappdevassignment.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var name: String = "",
    var password: String = "",
    var collections: List<LegoCollection> = emptyList(),
    var sets: List<LegoSet> = emptyList()
) {
    fun numberOfSets(): Int = sets.size

    fun numberOfCollections(): Int = collections.size

    override fun toString(): String {
        return "Name: $name, Password: $password, Collections: $collections, Sets: $sets"
    }
}