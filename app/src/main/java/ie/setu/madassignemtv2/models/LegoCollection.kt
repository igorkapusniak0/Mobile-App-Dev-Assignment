package ie.setu.madassignemtv2.models

import kotlinx.serialization.Serializable

@Serializable
data class LegoCollection(
    private var _name : String = "",
    private var _description : String = "",
    var creationDate : String = "",
    var sets: MutableList<LegoSet> = mutableListOf(),
    var isPublic: Boolean = false
) {

    var name: String
        get() = _name
        set(value) {
            require(value.length <= 20 && value.length >= 2)
            _name = value
        }

    var description: String
        get() = _description
        set(value) {
            require(value.length <= 30 && value.length >= 5)
            _description = value
        }

    fun numberOfSets() : Int {
        val numberOfSets : Int = sets.size
        return numberOfSets
    }

    override fun toString(): String {
        return  name + ", " +
                description + ", " +
                creationDate + ", " +
                sets
    }

}
