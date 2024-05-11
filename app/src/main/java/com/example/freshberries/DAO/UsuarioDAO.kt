package com.example.freshberries.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.freshberries.Modelo.Usuario

@Dao
interface UsuarioDAO {

    @Query("SELECT * FROM usuario")
    fun obtenerUsuarios(): List<Usuario>

    @Insert
    fun registrarUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuario WHERE id = :id")
    fun buscarUsuario(id:Long) : Usuario?

    @Update
    fun actualizarUsuario(usuario: Usuario)

    @Delete
    fun eliminarUsuario(usuario: Usuario)

    @Query("SELECT * FROM usuario WHERE usuario LIKE :usuario")
    fun buscarUsuarioPorUser(usuario: String) : Usuario
}