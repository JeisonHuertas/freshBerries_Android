package com.example.freshberries.Activities.ui.DetalleVenta

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.R

class AddProductoActivity : AppCompatActivity() {

    private lateinit var db: FreshBerriesBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_producto)


        var id : String? = ""
        val intent = intent

        //Se valida que el dato envíado desde el ProveedoresFragmet tenga un valor con la llave id
        if (intent.hasExtra("id_venta")){

            //Se guarda el valor envíado en una variable para trabajar con ella el método de buscar en el DAO
            id = intent.getStringExtra("id_venta")
        }

        db = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()


        val btnAddProducto = findViewById<Button>(R.id.btnAnadirProducto)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewDetalles)
        val adapter = DetallesAdapter(application)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        btnAddProducto.setOnClickListener {
            val nextActivity = ProductosDisponiblesActivity::class.java
            val intent = Intent(this, nextActivity)
            intent.putExtra("id_venta", id)

            startActivity(intent)
        }
    }

    fun actualizarTabla(tableLayout: TableLayout){
        var detalles = db.detalleVentaDAO.obtenerDetalle_ventasPorIdVenta(db.ventaDAO.obtenerUltimaVentaRegistrada())

        if(detalles.isNotEmpty()) {
            for (i in detalles){
                val nombre = db.productoDAO.buscarProducto(i.producto_id)?.nombre
                val cantidad = i.cantidad
                val subTotal = i.precio_venta

                val tableRow = TableRow(this)
                val txtViewNombre = TextView(this)
                val txtViewCantidad = TextView(this)
                val txtViewSubTotal = TextView(this)

                tableRow.addView(txtViewNombre)
                tableRow.addView(txtViewCantidad)
                tableRow.addView(txtViewSubTotal)

                tableLayout.addView(tableRow)
                Toast.makeText(this, "${detalles.size}", Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this, "No se han añadido productos", Toast.LENGTH_SHORT).show()
        }
    }
}