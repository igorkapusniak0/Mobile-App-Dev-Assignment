package ie.setu.madassignemtv2.utilities

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.models.LegoCollection
import ie.setu.madassignemtv2.models.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Utils(private val context: Context) {
    val saveFileName = "saveFile.JSON"
    private var globalData = GlobalData

    fun saveUsersToFile() {
        val jsonString = Json.encodeToString(GlobalData.usersData)
        val file = File(context.filesDir, saveFileName)
        file.writeText(jsonString)
        Log.d("Save Data",file.readText())
        Log.d("SaveFile", "Saved JSON to ${file.absolutePath}")
    }

    fun loadSaveFileToList(){
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

    fun isLoggedInUser(username: String): Boolean{
        var retVal = false
        if (username == globalData.loggedUserData.name){
            retVal = true
        }
        return retVal
    }

    fun getUserByName(userName: String): User{
        var retUser = User ()
        for(user in globalData.usersData){
            if (userName == user.name){
                retUser = user
                break
            }
        }
        return retUser
    }
    fun getDate(): String{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val current = LocalDateTime.now().format(formatter)
        return current
    }



}