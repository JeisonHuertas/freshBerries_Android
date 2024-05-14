package com.example.freshberries.Activities.ui.ventas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.Modelo.Proveedor
import com.example.freshberries.Modelo.Venta
import com.example.freshberries.R
import java.util.Date

class BuscarVentaActivity : AppCompatActivity() {

    private lateinit var bd: FreshBerriesBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar_venta)


        val txtIdVenta = findViewById<EditText>(R.id.txtIdVenta)
        val txtIdEmpleado = findViewById<EditText>(R.id.txtIdEmpleado)
        val txtIdCliente = findViewById<EditText>(R.id.txtClienteId)
        val txtTotalVenta = findViewById<EditText>(R.id.txtTotalVenta)
        val txtFechaVenta = findViewById<EditText>(R.id.txtFechaVenta)

        val btnActualizar = findViewById<Button>(R.id.btnActualizarVenta)
        var fechaSeleccionada : Date = Date()

        var id : String? = ""
        val intent = intent

        //Se valida que el dato envíado desde el ProveedoresFragmet tenga un valor con la llave id
        if (intent.hasExtra("id")){

            //Se guarda el valor envíado en una variable para trabajar con ella el método de buscar en el DAO
            id = intent.getStringExtra("id")
        }

        //Instancia de la Base de datos
        bd = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()

        if(id != null) {
            val busqueda: Venta? = bd.ventaDAO.buscarVenta(id.toLong())
            if (busqueda != null) {
                txtIdVenta.setText(busqueda.id.toString())
                txtFechaVenta.setText(busqueda.fecha_venta.toString())
                txtIdCliente.setText(busqueda.cliente_id.toString())
                txtIdEmpleado.setText(busqueda.empleado_id.toString())
                txtTotalVenta.setText(busqueda.total.toString())
            }
        }

    }
}