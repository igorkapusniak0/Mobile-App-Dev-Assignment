package ie.setu.mobileappdevassignment.models

class User {
    var name : String = ""
    var password : String = ""
    var collections = arrayOf<LegoCollection>()
    var sets = arrayOf<LegoSet>()

    fun numberOfSets() : Int {
        val numberOfSets : Int = sets.size
        return numberOfSets
    }

    fun numberOfCollections() : Int {
        val numberOfCollections : Int = collections.size
        return numberOfCollections
    }

    override fun toString(): String {
        return "Name: " + name +
                "Password: " + password +
                "Collections: " + collections +
                "Sets: " + sets
    }
}