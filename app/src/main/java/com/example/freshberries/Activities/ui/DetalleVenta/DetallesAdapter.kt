package com.example.freshberries.Activities.ui.DetalleVenta

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.R

class DetallesAdapter(context: Context): RecyclerView.Adapter<DetallesAdapter.ViewHolder>() {

    private var db: FreshBerriesBD = Room.databaseBuilder(context, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()

    private var detalles = db.detalleVentaDAO.obtenerDetalle_ventasPorIdVenta(db.ventaDAO.obtenerUltimaVentaRegistrada())


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemNombre: TextView
        var itemCantidad: TextView
        var itemSubTotal: TextView

        init {
            itemNombre = itemView.findViewById(R.id.nombre)
            itemCantidad = itemView.findViewById(R.id.cantidad)
            itemSubTotal = itemView.findViewById(R.id.subTotal)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_detalles, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return detalles.size
    }

    override fun onBindViewHolder(viewHolder: DetallesAdapter.ViewHolder, i: Int) {
        viewHolder.itemNombre.text = db.productoDAO.buscarProducto(detalles[i].producto_id)?.nombre
        viewHolder.itemCantidad.text = detalles[i].cantidad.toString()
        viewHolder.itemSubTotal.text = detalles[i].precio_venta.toString()

    }
}