package ie.setu.madassignemtv2.models

import kotlinx.serialization.Serializable

@Serializable
data class LegoSet(
    private var _name : String = "",
    private var _setNumber : Int = 0,
    private var _pieceCount : Int = 0,
    private var _age : Int = 0,
    private var _price : Float = 0f,
    var isPublic : Boolean = false,
    var collectionName: String = ""

) {

    var name: String
        get() = _name
        set(value) {
            require(value.length <= 20 && value.length >= 5)
            _name = value
        }

    var setNumber: Int
        get() = _setNumber
        set(value) {
            require(value >= 1 && value <= 9999999 )
            _setNumber = value
        }

    var pieceCount: Int
        get() = _pieceCount
        set(value) {
            require(value >= 50 && value <= 999999)
            _pieceCount = value
        }

    var age: Int
        get() = _age
        set(value) {
            require(value >= 3 && value <= 18)
            _age = value
        }

    var price: Float
        get() = _price
        set(value) {
            require(value >= 1.0f &&  value <= 99999.0f)
            _price = value
        }

    override fun toString(): String {
        return  name + ", " +
                setNumber + ", " +
                pieceCount + ", " +
                age + ", " +
                price + ", " +
                isPublic
    }

}