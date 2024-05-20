package com.example.freshberries.Activities.ui.ventas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.freshberries.Activities.ui.DetalleVenta.DetallesAdapter
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.R
import java.text.SimpleDateFormat

class VentasAdapter(context: Context) : RecyclerView.Adapter<VentasAdapter.ViewHolder>() {

    private var db: FreshBerriesBD = Room.databaseBuilder(context, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()

    private var ventas = db.ventaDAO.obtenerVentas()

    val simpleDateFormat = SimpleDateFormat("dd / MM / yyyy")

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemIdVentas: TextView
        var itemFechaVenta: TextView
        var itemIdCliente: TextView
        var itemTotalVenta : TextView

        init {
            itemIdVentas = itemView.findViewById(R.id.txtVwIdVenta)
            itemFechaVenta = itemView.findViewById(R.id.txtVwFechaVenta)
            itemIdCliente = itemView.findViewById(R.id.txtVwIdCliente)
            itemTotalVenta = itemView.findViewById(R.id.txtVwTotalVenta)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_ventas, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return ventas.size
    }

    override fun onBindViewHolder(viewHolder: VentasAdapter.ViewHolder, i: Int) {
        val fechaString = simpleDateFormat.format(ventas[i].fecha_venta)
        viewHolder.itemIdVentas.text = ventas[i].id.toString()
        viewHolder.itemFechaVenta.text = fechaString
        viewHolder.itemIdCliente.text = ventas[i].cliente_id.toString()
        viewHolder.itemTotalVenta.text = ventas[i].total.toString()
    }
}