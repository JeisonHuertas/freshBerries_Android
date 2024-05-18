package com.example.freshberries.Activities.ui.ventas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.Modelo.Proveedor
import com.example.freshberries.Modelo.Venta
import com.example.freshberries.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class BuscarVentaActivity : AppCompatActivity() {

    private lateinit var bd: FreshBerriesBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar_venta)


        val simpleDateFormat = SimpleDateFormat("dd / MM / yyyy")


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

                val fechaString = simpleDateFormat.format(busqueda.fecha_venta)

                txtIdVenta.setText(busqueda.id.toString())
                txtFechaVenta.setText(fechaString)
                txtIdCliente.setText(busqueda.cliente_id.toString())
                txtIdEmpleado.setText(busqueda.empleado_id.toString())
                txtTotalVenta.setText(busqueda.total.toString())
            }
        }


        txtFechaVenta.setOnClickListener {
            fun onDateSelected(day: Int, month: Int, year: Int) {

                txtFechaVenta.setText("$day / $month / $year")
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                fechaSeleccionada = calendar.time


            }
            fun mostrarDatePickerDialog(){

                val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
                datePicker.show(supportFragmentManager, "datePicker")

            }
            mostrarDatePickerDialog()

        }

        btnActualizar.setOnClickListener {
            val ventaId = txtIdVenta.text.toString().toLongOrNull()
            val fechaVenta = fechaSeleccionada
            val empleadoId = txtIdEmpleado.text.toString()
            val clienteId = txtIdCliente.text.toString()
            val totalVenta = txtTotalVenta.text.toString()


            if (ventaId != null && empleadoId.isNotEmpty() && clienteId.isNotEmpty() && totalVenta.isNotEmpty()){
                val venta = Venta(ventaId,fechaVenta,empleadoId.toLong(),clienteId.toLong(), totalVenta.toLong())
                try {
                    bd.ventaDAO.actualizarVenta(venta)
                }catch (ex : Exception){
                    Toast.makeText(this, "Ya existe una venta con ese ID", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this, "Se actualizó la venta", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(this, "Campos vacíos", Toast.LENGTH_SHORT).show()
            }
        }


    }
}