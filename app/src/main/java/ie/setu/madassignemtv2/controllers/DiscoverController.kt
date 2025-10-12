package ie.setu.madassignemtv2.controllers

import android.content.Context
import ie.setu.madassignemtv2.models.LegoCollection
import ie.setu.madassignemtv2.models.LegoSet
import ie.setu.madassignemtv2.utilities.GlobalData
import ie.setu.madassignemtv2.utilities.Utils

class DiscoverController(context: Context) {
    private var globalData = GlobalData
    private val utils = Utils(context)

    fun getAllPublicSets(): MutableList<LegoSet>{
        val sets = mutableListOf<LegoSet>()
        for(users in globalData.usersData){
            if (users.name != globalData.loggedUserData.name){
                for (collection in users.collections){
                    for (set in collection.sets){
                        if(set.isPublic){
                            sets.add(set)
                        }
                    }
                }
            }
        }
        return sets
    }

    fun getAllPublicCollections(): MutableList<LegoCollection>{
        val collections = mutableListOf<LegoCollection>()
        for(users in globalData.usersData){
            if (users.name != globalData.loggedUserData.name){
                for (collection in users.collections){
                    if(collection.isPublic){
                        collections.add(collection)

                    }
                }
            }
        }
        return collections
    }


}