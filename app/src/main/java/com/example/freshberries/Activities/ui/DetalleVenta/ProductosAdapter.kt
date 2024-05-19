package com.example.freshberries.Activities.ui.DetalleVenta

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.Modelo.Detalle_Venta
import com.example.freshberries.R

class ProductosAdapter(context: Context, id_venta: String) : RecyclerView.Adapter<ProductosAdapter.ViewHolder>() {



    private var context : Context = context
    private var id_venta : Long = id_venta.toLong()


    private var db: FreshBerriesBD = Room.databaseBuilder(context, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()


    private var productos = db.productoDAO.obtenerProductos()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemNombreProducto: TextView
        var itemPrecioVenta: TextView
        var itemStock: EditText

        init {
            itemNombreProducto = itemView.findViewById(R.id.nombreProducto)
            itemPrecioVenta = itemView.findViewById(R.id.precioDeVenta)
            itemStock = itemView.findViewById(R.id.stock)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_element, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        viewHolder.itemNombreProducto.text = productos[i].nombre
        viewHolder.itemPrecioVenta.text = productos[i].precio_venta.toString()
        //viewHolder.itemStock.text = productos[i].stock.toString()

        viewHolder.itemView.setOnClickListener{

            productos = db.productoDAO.obtenerProductos()

            if (productos[i].stock >= viewHolder.itemStock.text.toString().toLong() ){
                val Detalle_Venta = Detalle_Venta(id_venta,productos[i].id, viewHolder.itemStock.text.toString().toInt(),productos[i].precio_venta.toLong() * viewHolder.itemStock.text.toString().toInt() )
                db.detalleVentaDAO.registrarDetalle_venta(Detalle_Venta)
                db.productoDAO.actualizarStockProducto(viewHolder.itemStock.text.toString().toInt(),productos[i].id)
                Toast.makeText(context,"Producto Añadido",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,"Stock disponible: ${productos[i].stock}",Toast.LENGTH_SHORT).show()
            }


        }
    }

}