package com.example.fetchquest.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.fetchquest.model.ListItem
import com.example.fetchquest.service.ApiModule
import com.google.gson.Gson
import java.util.HashMap

class MainViewModel : ViewModel() {
    private val api = ApiModule()

    suspend fun getListItems(): HashMap<Int, MutableList<ListItem>>  {
        val json = api.getList()
        val itemList = Gson().fromJson(json, Array<ListItem>::class.java).toMutableList()
        val itemMap = hashMapOf<Int, MutableList<ListItem>>()

        itemList.forEach {
            if (itemMap[it.listId].isNullOrEmpty()) {
                itemMap[it.listId] = mutableListOf()
            }
            itemMap[it.listId]!!.add(it)
        }
        itemMap.keys.forEach { key ->
            itemMap[key] = itemMap[key]?.sortedWith(compareBy({it.id}, {it.name}))?.toMutableList()
                ?: mutableListOf()
            itemMap[key]?.removeAll { it.name.isNullOrEmpty() }
            Log.d("XYZ", key.toString())
        }
        return itemMap
    }

}