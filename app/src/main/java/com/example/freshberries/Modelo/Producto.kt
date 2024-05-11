package com.example.freshberries.Modelo

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Proveedor::class,
            parentColumns = ["id"],
            childColumns = ["proveedor_id"]
        )
    ]
)
data class Producto (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Long = 0,

    @ColumnInfo(name = "nombre")
    @NonNull
    var nombre : String = "",

    @ColumnInfo(name = "descripcion")
    @NonNull
    var descripcion : String = "",

    @ColumnInfo(name = "precio_compra")
    @NonNull
    var precio_compra : Int = 0,

    @ColumnInfo(name = "precio_venta")
    @NonNull
    var precio_venta : Int = 0,

    @ColumnInfo(name = "stock")
    @NonNull
    var stock : Int = 0,

    @ColumnInfo(name = "proveedor_id")
    @NonNull
    var proveedor_id : Long = 0

){
    constructor(nombre: String, descripcion: String, precio_compra: Int, precio_venta: Int, stock: Int, proveedor_id: Long): this(){
        this.nombre = nombre
        this.descripcion = descripcion
        this.precio_compra = precio_compra
        this.precio_venta = precio_venta
        this.stock = stock
        this.proveedor_id = proveedor_id
    }
}