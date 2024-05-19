package com.example.freshberries.Activities.ui.proveedores

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

class ListarProveedorActivity : AppCompatActivity() {

    private lateinit var db: FreshBerriesBD
    private lateinit var tabla: TableLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listar_proveedor)

        tabla = findViewById<TableLayout>(R.id.tabla)


        db = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME)
            .allowMainThreadQueries().build()


        val clientes = db.proveedorDAO.obtenerProveedores()




        for (cliente in clientes) {
            val fila = TableRow(this)

            val textViewCodigo = TextView(this).apply { text = cliente.id.toString() }
            val textViewNombre = TextView(this).apply { text = cliente.nombre }
            val textViewTelefono = TextView(this).apply { text = cliente.telefono }
            val textViewDireccion = TextView(this).apply { text = cliente.direccion }


            fila.addView(textViewCodigo)
            fila.addView(textViewNombre)
            fila.addView(textViewTelefono)
            fila.addView(textViewDireccion)



            tabla.addView(fila)




            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.listRecycleViewProductos)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

    }
}