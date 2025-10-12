package ie.setu.madassignemtv2.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var name: String = "",
    var password: String = "",
    var collections: MutableList<LegoCollection> = mutableListOf(),
    var sets: MutableList<LegoSet> = mutableListOf()
) {

    fun numberOfCollections(): Int = collections.size

    override fun toString(): String {
        return "Name: $name, Password: $password, Collections: $collections"
    }
}