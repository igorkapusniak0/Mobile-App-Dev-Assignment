package ie.setu.mobileappdevassignment.utilities

import android.content.Context
import android.util.Log
import ie.setu.mobileappdevassignment.models.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Utils(private val context: Context) {
    val saveFileName = "saveFile.JSON"

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

    fun getDate(): String{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val current = LocalDateTime.now().format(formatter)
        return current
    }
}