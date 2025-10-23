package ie.setu.madassignemtv2.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    private var _name: String = "",
    private var _password: String = "",
    var darkMode: Boolean = false,
    private var _language: String = "eng",
    var collections: MutableList<LegoCollection> = mutableListOf(),
    var sets: MutableList<LegoSet> = mutableListOf()
) {

    var name: String
        get() = _name
        set(value) {
            require(value.length <= 20 && value.length >= 2)
            _name = value
        }

    var password: String
        get() = _password
        set(value) {
            require(value.length <= 30 && value.length >= 5)
            _password = value
        }

    var language: String
        get() = _language
        set(value) {
            require(value == "eng" || value == "pl")
            _language = value
        }

    override fun toString(): String {
        return name + ", " +
                password + ", " +
                language + ", " +
                darkMode.toString() + ", " +
                collections.toString() + ", " +
                sets.toString() + ", "
    }
}