package ie.setu.madassignemtv2.utilities


import ie.setu.madassignemtv2.models.User
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
