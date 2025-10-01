package ie.setu.mobileappdevassignment.utilities

import ie.setu.mobileappdevassignment.models.User
import kotlinx.serialization.Serializable

@Serializable
object GlobalData {
    val usersData = mutableListOf<User>()
    var loggedUserData = User()

    override fun toString(): String {
        var users = ""
        for (user in usersData){
            users += (user.name + ", ")
        }
        return users
    }
}

