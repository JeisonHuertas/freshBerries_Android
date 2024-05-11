package com.example.freshberries.Modelo

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cliente (

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "id")
    var id : Long = 0,

    @ColumnInfo(name = "nombre")
    @NonNull
    var nombre : String = "",

    @ColumnInfo(name = "telefono")
    @NonNull
    var telefono : String = "",

    @ColumnInfo(name = "direccion")
    @NonNull
    var direccion : String = ""
)