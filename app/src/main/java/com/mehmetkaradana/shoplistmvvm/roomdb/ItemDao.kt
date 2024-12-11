package com.mehmetkaradana.shoplistmvvm.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mehmetkaradana.shoplistmvvm.model.Item

@Dao
interface ItemDao {

    @Query("SELECT name, id FROM item")
    fun getItemWithNameAndId() : List<Item>

    // :id sağlanan id değeri
    @Query("SELECT * From item WHERE id = :id")
    fun getItemById(id : Int) : Item

    @Insert
    suspend fun insert(item: Item)

    @Delete
    suspend fun delete(item: Item)
}