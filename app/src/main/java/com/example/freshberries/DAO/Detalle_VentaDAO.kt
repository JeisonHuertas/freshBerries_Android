package com.example.freshberries.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.freshberries.Modelo.Detalle_Venta

@Dao
interface Detalle_VentaDAO {

    @Query("SELECT * FROM detalle_venta")
    fun obtenerDetalle_ventas(): List<Detalle_Venta>

    @Insert
    fun registrarDetalle_venta(detalleVenta: Detalle_Venta)

    @Query("SELECT * FROM detalle_venta WHERE id = :id")
    fun buscarDetalle_venta(id:Long) : Detalle_Venta?

    @Update
    fun actualizarDetalle_venta(detalle_Venta:Detalle_Venta)

    @Delete
    fun eliminarDetalle_venta(detalle_Venta:Detalle_Venta)

    @Query("SELECT * FROM detalle_venta WHERE venta_id = :id")
    fun obtenerDetalle_ventasPorIdVenta(id: Long): List<Detalle_Venta>
}