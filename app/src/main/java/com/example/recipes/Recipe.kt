package com.example.recipes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
class Recipe(var img : String, var tittle : String, var ing : String, var des : String, var category : String) {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var uid = 0
}