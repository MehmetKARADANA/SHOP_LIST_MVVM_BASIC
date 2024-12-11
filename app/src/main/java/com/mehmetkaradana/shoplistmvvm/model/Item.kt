package com.mehmetkaradana.shoplistmvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Item(
    @ColumnInfo("name")
    var itemName : String,

    @ColumnInfo("store")
    var storeName : String?,

    var price : Int?,

    var image :ByteArray?,
) {
    @PrimaryKey(autoGenerate = true )
    var id: Int=0
}