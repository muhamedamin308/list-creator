package com.example.listmaker

import android.content.Context
import androidx.preference.PreferenceManager
import java.util.prefs.Preferences

class ListDataManager (private val context : Context) {
    fun saveList(list : TaskList) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPreferences.putStringSet(list.name , list.task.toHashSet())
        sharedPreferences.apply()
    }
    fun readList() : ArrayList<TaskList>{
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val sharedPreferencesContents = sharedPreferences.all
        val taskLists = ArrayList<TaskList>()

        for (taskList in sharedPreferencesContents){
            val itemsHashSet = ArrayList(taskList.value as HashSet<String>)
            val list = TaskList(taskList.key , itemsHashSet)
            taskLists.add(list)
        }
        return taskLists
    }
}