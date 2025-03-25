package com.example.aplikasicrud.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
