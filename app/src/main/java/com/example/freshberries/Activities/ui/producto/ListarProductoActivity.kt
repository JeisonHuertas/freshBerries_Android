package com.example.freshberries.Activities.ui.producto

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.R

class ListarProductoActivity : AppCompatActivity() {
    private lateinit var db: FreshBerriesBD
    private lateinit var tabla: TableLayout
    private lateinit var tabla2: TableLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listar_producto)

        tabla = findViewById<TableLayout>(R.id.tabla)
        tabla2 = findViewById<TableLayout>(R.id.tabla2)


        db = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME)
            .allowMainThreadQueries().build()


        val clientes = db.productoDAO.obtenerProductos()




        for (producto in clientes) {
            val fila = TableRow(this)

            val textViewCodigo = TextView(this).apply { text = producto.id.toString() }
            val textViewNombre = TextView(this).apply { text = producto.nombre }
            val textViewDescripcion = TextView(this).apply { text = producto.descripcion }
            val textViewPrecioCompra = TextView(this).apply { text = producto.precio_compra.toString() }



            fila.addView(textViewCodigo)
            fila.addView(textViewNombre)
            fila.addView(textViewDescripcion)
            fila.addView(textViewPrecioCompra)



            tabla.addView(fila)




            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
        for (producto in clientes) {
            val fila = TableRow(this)

            val textViewPrecioVenta = TextView(this).apply { text = producto.precio_venta.toString() }
            val textVievStock = TextView(this).apply { text = producto.stock.toString() }
            val textViewidProveedor = TextView(this).apply { text = producto.proveedor_id.toString() }



            fila.addView(textViewPrecioVenta)
            fila.addView(textVievStock)
            fila.addView(textViewidProveedor)




            tabla2.addView(fila)





        }


    }
}