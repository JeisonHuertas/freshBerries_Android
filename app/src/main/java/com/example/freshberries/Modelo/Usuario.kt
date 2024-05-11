package com.example.freshberries.Modelo

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Usuario (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Long = 0,

    @ColumnInfo(name = "nombre")
    @NonNull
    var nombre : String = "",

    @ColumnInfo(name = "usuario")
    @NonNull
    var usuario : String = "",

    @ColumnInfo(name = "contrasena")
    @NonNull
    var contrasena : String = "",

    @ColumnInfo(name = "perfil")
    @NonNull
    var perfil : String = ""
){
    constructor(nombre: String, usuario: String, contrasena: String, perfil: String):this(){
        this.nombre = nombre
        this.usuario = usuario
        this.contrasena = contrasena
        this.perfil = perfil
    }
}