package com.example.freshberries.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.freshberries.Modelo.Cliente

@Dao
interface ClienteDAO {

    @Query("SELECT * FROM Cliente")
    fun obtenerClientes(): List<Cliente>

    @Insert
    fun registrarCliente(cliente: Cliente)

    @Query("SELECT * FROM cliente WHERE id = :id")
    fun buscarCliente(id:Long) : Cliente?

    @Update
    fun actualizarCliente(cliente: Cliente)

    @Delete
    fun eliminarCliente(cliente: Cliente)

}