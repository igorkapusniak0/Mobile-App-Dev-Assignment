package ie.setu.mobileappdevassignment.controllers

import android.content.Context
import android.util.Log
import java.io.File
import ie.setu.mobileappdevassignment.utilities.GlobalData
import ie.setu.mobileappdevassignment.models.User
import ie.setu.mobileappdevassignment.utilities.Utils



class LoginController {
    val saveFileName = "saveFile.JSON"
    private var globalData = GlobalData
    private lateinit var user: User
    private lateinit var utils: Utils

    fun loginUser(username: String, password: String): Int{
        var loginSuccessful = 0
        for (user in globalData.usersData){
            if (userExists(username)){
                if (password == user.password){
                    Log.d("Login", "successful")
                    loginSuccessful = 1
                    globalData.loggedUserData = user
                    break
                }
                else{
                    loginSuccessful = 2
                }
            }
        }
        return loginSuccessful
    }



    private fun userExists(username: String) : Boolean{
        var userExists = false
        for (user in globalData.usersData){
            if (username.lowercase() == user.name.lowercase()){
                userExists = true
                break
            }

        }
        return userExists
    }
    fun registerUser(context: Context, username : String, password: String): Boolean{
        var registerSuccessful = false
        val file = File(context.filesDir,saveFileName)
        if (!file.exists()) {
            file.createNewFile()
            file.writeText("") // optional
            Log.d("SaveFile", "File created at ${file.absolutePath}")
        }

        if (!userExists(username)){
            user = User()
            user.name = username
            user.password = password

            registerSuccessful = true
            globalData.usersData.add(user)
            globalData.loggedUserData = user
            utils.saveUsersToFile(context, saveFileName)
        }
        return registerSuccessful
    }
}