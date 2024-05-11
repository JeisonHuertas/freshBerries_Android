package com.example.freshberries.Modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["empleado_id"]
        ),
        ForeignKey(
            entity = Cliente::class,
            parentColumns = ["id"],
            childColumns = ["cliente_id"]
        ),
    ]
)
data class Venta (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Long = 0,

    @ColumnInfo(name = "fecha_venta")
    var fecha_venta : Date = Date(2024, 4, 11),

    @ColumnInfo(name = "empleado_id")
    var empleado_id : Long =0,

    @ColumnInfo(name = "cliente_id")
    var cliente_id : Long =0,

    @ColumnInfo(name = "total")
    var total : Long =0

){
    constructor(fecha_venta: Date, empleado_id: Long, cliente_id: Long, total: Long):this(){
        this.fecha_venta = fecha_venta
        this.empleado_id = empleado_id
        this.cliente_id = cliente_id
        this.total = total
    }
}