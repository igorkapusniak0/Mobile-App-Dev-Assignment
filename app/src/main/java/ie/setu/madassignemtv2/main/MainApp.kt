package ie.setu.madassignemtv2.main

import android.app.Application
import android.util.Log
import ie.setu.madassignemtv2.models.User


class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.i("App started", "True")
    }
}
