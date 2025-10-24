package ie.setu.madassignemtv2.utilities

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomsheet.BottomSheetDialog
import ie.setu.madassignemtv2.R
import ie.setu.madassignemtv2.models.LegoCollection
import ie.setu.madassignemtv2.models.User
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.intellij.lang.annotations.Language
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Utils(private val context: Context) {
    val saveFileName = "saveFile.JSON"
    private var globalData = GlobalData

    fun saveUsersToFile() {
        val index = GlobalData.usersData.indexOfFirst { it.name == GlobalData.loggedUserData.name }
        if (index != -1) {
            GlobalData.usersData[index] = GlobalData.loggedUserData
        }
        val jsonString = Json.encodeToString(GlobalData.usersData)
        val file = File(context.filesDir, saveFileName)
        file.writeText(jsonString)
    }

    fun loadSaveFileToList(){
        globalData.usersData.clear()
        val file = File(context.filesDir, saveFileName)
        if (file.exists()){
            val jsonString = file.readText()
            if (jsonString.isNotBlank()){
                val users = Json.decodeFromString<List<User>>(jsonString)
                GlobalData.usersData.addAll(users)
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

    fun getLanguage(): String{
        return globalData.loggedUserData.language
    }
    fun setLanguage(language: String){
        globalData.loggedUserData.language = language
        saveUsersToFile()
    }

    fun deleteAccount(){
        globalData.usersData.remove(globalData.loggedUserData)
        saveUsersToFile()
    }

    fun toggleDarkMode(){
        val currentMode = AppCompatDelegate.getDefaultNightMode()
        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            globalData.loggedUserData.darkMode = false
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            globalData.loggedUserData.darkMode = true
        }
        saveUsersToFile()
    }

    fun setDarkMode(){
        if (globalData.loggedUserData.darkMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun getDate(): String{
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val current = LocalDateTime.now().format(formatter)
        return current
    }



}