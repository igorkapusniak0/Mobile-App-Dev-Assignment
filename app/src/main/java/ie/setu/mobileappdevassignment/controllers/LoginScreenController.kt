package ie.setu.mobileappdevassignment.controllers

import android.content.Context
import android.util.Log
import java.io.File
import ie.setu.mobileappdevassignment.globalData.GlobalData
import ie.setu.mobileappdevassignment.models.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class LoginScreenController {
    private val saveFileName = "saveFile.JSON"
    private var globalData = GlobalData
    private lateinit var user: User

    fun loadSaveFileToList(context: Context){
        val file = File(context.filesDir, saveFileName)
        Log.d("file", "file")
        if (file.exists()){
            Log.d("file", "file exists")
            val jsonString = file.readText()
            if (jsonString.isNotBlank()){
                Log.d("file", "not blank")
                val users = Json.decodeFromString<List<User>>(jsonString)
                GlobalData.usersData.addAll(users)
                Log.d("userdata Loaded", GlobalData.usersData.toString())
            }
        }

    }

    fun loginUser(username: String, password: String){
        for (user in globalData.usersData){
            if (username.lowercase() == user.name.lowercase()){
                Log.d("Login", "successful")
                break
            }
            else{
                Log.d("Login", "Failed")
            }
        }
    }

    fun saveUsersToFile(context: Context) {
        val jsonString = Json.encodeToString(GlobalData.usersData)
        val file = File(context.filesDir, saveFileName)
        file.writeText(jsonString)
        Log.d("savedata",file.readText())
        Log.d("SaveFile", "Saved JSON to ${file.absolutePath}")
    }
    fun registerUser(context: Context, username : String, password: String){
        val file = File(context.filesDir,saveFileName)
        if (!file.exists()) {
            file.createNewFile()
            file.writeText("") // optional
            Log.d("SaveFile", "File created at ${file.absolutePath}")
        }

        user = User()
        user.name = username
        user.password = password

        globalData.usersData.add(user)
        saveUsersToFile(context)
    }
}