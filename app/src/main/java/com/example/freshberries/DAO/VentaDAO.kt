package com.example.freshberries.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.freshberries.Modelo.Venta

@Dao
interface VentaDAO {

    @Query("SELECT * FROM venta")
    fun obtenerVentas(): List<Venta>

    @Insert
    fun registrarVenta(venta: Venta)

    @Query("SELECT id FROM Venta ORDER BY id DESC LIMIT 1")
    fun obtenerUltimaVentaRegistrada() : Long

    @Query("SELECT * FROM venta WHERE id = :id")
    fun buscarVenta(id:Long) : Venta?

    @Update
    fun actualizarVenta(venta: Venta)

    @Delete
    fun eliminarVenta(venta: Venta)
}