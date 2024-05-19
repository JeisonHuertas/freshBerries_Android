package com.example.freshberries.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import com.example.freshberries.Modelo.Producto

@Dao
interface ProductoDAO {

    @Query("SELECT * FROM producto")
    fun obtenerProductos(): List<Producto>

    @Insert
    fun registrarProducto(producto: Producto)

    @Query("SELECT * FROM producto WHERE id = :id")
    fun buscarProducto(id:Long) : Producto?

    @Update
    fun actualizarProducto(producto: Producto)

    @Delete
    fun eliminarProducto(producto: Producto)

    @Query("UPDATE producto SET stock = stock - :x WHERE id = :id")
    fun actualizarStockProducto(x : Int , id : Long)

}