package com.example.freshberries.Configuracion

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.freshberries.DAO.ClienteDAO
import com.example.freshberries.DAO.Detalle_VentaDAO
import com.example.freshberries.DAO.ProductoDAO
import com.example.freshberries.DAO.ProveedorDAO
import com.example.freshberries.DAO.UsuarioDAO
import com.example.freshberries.DAO.VentaDAO
import com.example.freshberries.Modelo.Cliente
import com.example.freshberries.Modelo.Detalle_Venta
import com.example.freshberries.Modelo.Producto
import com.example.freshberries.Modelo.Proveedor
import com.example.freshberries.Modelo.Usuario
import com.example.freshberries.Modelo.Venta

@Database(
    entities = [Cliente::class, Detalle_Venta::class, Producto::class, Proveedor::class, Usuario::class, Venta::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class FreshBerriesBD:RoomDatabase(){

    abstract val clienteDAO : ClienteDAO
    abstract val detalleVentaDAO : Detalle_VentaDAO
    abstract val productoDAO : ProductoDAO
    abstract val proveedorDAO : ProveedorDAO
    abstract val usuarioDAO : UsuarioDAO
    abstract val ventaDAO : VentaDAO

    companion object{
        const val DATABASE_NAME = "BD_FreshBerries"
    }

}