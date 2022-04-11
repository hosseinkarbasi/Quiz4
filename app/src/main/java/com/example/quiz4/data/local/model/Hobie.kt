package com.example.quiz4.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_hobie")
data class Hobie(
    @PrimaryKey
    val id: Int,
    val name: String,
    @ColumnInfo(name = "user_id")
    val userId:String
)