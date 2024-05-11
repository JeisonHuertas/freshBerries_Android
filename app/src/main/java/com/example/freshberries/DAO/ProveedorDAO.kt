package com.example.freshberries.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.freshberries.Modelo.Detalle_Venta
import com.example.freshberries.Modelo.Proveedor

@Dao
interface ProveedorDAO {

    @Query("SELECT * FROM proveedor")
    fun obtenerProveedores(): List<Proveedor>

    @Insert
    fun registrarProveedor(proveedor: Proveedor)

    @Query("SELECT * FROM proveedor WHERE id = :id")
    fun buscarProveedor(id:Long) : Proveedor?

    @Update
    fun actualizarProveedor(proveedor: Proveedor)

    @Delete
    fun eliminarProveedor(proveedor: Proveedor)
}