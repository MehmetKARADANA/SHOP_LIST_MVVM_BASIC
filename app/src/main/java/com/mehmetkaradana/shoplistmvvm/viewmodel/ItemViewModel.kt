package com.mehmetkaradana.shoplistmvvm.viewmodel

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.mehmetkaradana.shoplistmvvm.model.Item
import com.mehmetkaradana.shoplistmvvm.roomdb.ItemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(getApplication(),
        ItemDatabase::class.java,
        "Items").build()

    val itemList = mutableStateOf<List<Item>>(listOf())
    val selectedItem = mutableStateOf<Item>(Item("","",0, ByteArray(0)))

    private val itemDao=db.itemDao()

    fun getItemList(){
        try {
            viewModelScope.launch (Dispatchers.IO){
                itemList.value = itemDao.getItemWithNameAndId()
            }
        }catch (e :Exception){
            e.printStackTrace()
        }

    }

    fun getItem(id : Int){
        viewModelScope.launch (Dispatchers.IO){
            try {
                val item = itemDao.getItemById(id)
                item?.let {
                    selectedItem.value = item
                }
            }catch (e : Exception){
                e.printStackTrace()
            }

        }
    }

    fun saveItem(item : Item) {
       try {
           viewModelScope.launch (Dispatchers.IO){
               itemDao.insert(item)
           }
       }catch (e :Exception){
           e.printStackTrace()
       }
    }

    fun deleteItem(item: Item) {
       try {
           viewModelScope.launch (Dispatchers.IO){
               itemDao.delete(item)
           }
       }catch (e : Exception){
           e.printStackTrace()
       }
    }
}