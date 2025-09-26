package ie.setu.mobileappdevassignment.globalData

import ie.setu.mobileappdevassignment.models.User
import kotlinx.serialization.Serializable

@Serializable
object GlobalData {
    val usersData = mutableListOf<User>()

    override fun toString(): String {
        var users: String = ""
        for (user in usersData){
            users += (user.name + ", ")
        }
        return users
    }
}

