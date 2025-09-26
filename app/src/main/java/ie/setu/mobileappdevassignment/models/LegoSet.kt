package ie.setu.mobileappdevassignment.models

import kotlinx.serialization.Serializable

@Serializable
data class LegoSet(
    var name : String = "",
    var setNumber : Int = 0,
    var pieceCount : Int = 0,
    var ageRange : Int = 0,
    var price : Float = 0f,
    var image : String = "",
    var isPublic : Boolean = false
) {

    override fun toString(): String {
        return "Name: " + name +
                "Set Number: " + setNumber +
                "Pieces: " + pieceCount +
                "Age Range: " + ageRange +
                "Price: " + price +
                "Image: " + image +
                "Public: " + isPublic
    }

}
