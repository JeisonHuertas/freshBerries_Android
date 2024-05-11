package com.example.freshberries.Modelo

import androidx.annotation.NonNull
import androidx.core.content.contentValuesOf
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Venta::class,
            parentColumns = ["id"],
            childColumns = ["venta_id"]
        ),
        ForeignKey(
            entity = Producto::class,
            parentColumns = ["id"],
            childColumns = ["producto_id"]
        ),
    ]
)
data class Detalle_Venta (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Long = 0,

    @ColumnInfo(name = "venta_id")
    var venta_id : Long =0,

    @ColumnInfo(name = "producto_id")
    var producto_id : Long =0,

    @ColumnInfo(name = "cantidad")
    @NonNull
    var cantidad : Int = 0,


    @ColumnInfo(name = "precio_venta")
    @NonNull
    var precio_venta : Long = 0


){
    constructor(venta_id: Long, producto_id: Long, cantidad: Int, precio_venta: Long):this(){
        this.venta_id = venta_id
        this.producto_id = producto_id
        this.cantidad = cantidad
        this.precio_venta = precio_venta
    }
}