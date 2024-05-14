package com.example.freshberries.Activities.ui.ventas

import android.app.DatePickerDialog
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
import java.util.Calendar
import java.util.Date

class RegistrarVentaActivity : AppCompatActivity() {

    private lateinit var bd: FreshBerriesBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar_venta)

        val txtIdVenta = findViewById<EditText>(R.id.txtIdVenta)
        val txtIdEmpleado = findViewById<EditText>(R.id.txtIdEmpleado)
        val txtIdCliente = findViewById<EditText>(R.id.txtClienteId)
        val txtTotalVenta = findViewById<EditText>(R.id.txtTotalVenta)
        val txtFechaVenta = findViewById<EditText>(R.id.txtFechaVenta)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrarVenta)
        var fechaSeleccionada : Date = Date()

        //Instancia de la Base de datos
        bd = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()

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


        btnRegistrar.setOnClickListener {
            val ventaId = txtIdVenta.text.toString().toLongOrNull()
            val fechaVenta = fechaSeleccionada
            val empleadoId = txtIdEmpleado.text.toString()
            val clienteId = txtIdCliente.text.toString()
            val totalVenta = txtTotalVenta.text.toString()


            if (ventaId != null && empleadoId.isNotEmpty() && clienteId.isNotEmpty() && totalVenta.isNotEmpty()){
                val venta = Venta(ventaId,fechaVenta,empleadoId.toLong(),clienteId.toLong(), totalVenta.toLong())
                try {
                    bd.ventaDAO.registrarVenta(venta)
                }catch (ex : Exception){
                    Toast.makeText(this, "Ya existe una venta con ese ID", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this, "Se registró la venta", Toast.LENGTH_SHORT).show()

                txtIdVenta.setText("")
                txtIdCliente.setText("")
                txtIdEmpleado.setText("")
                txtFechaVenta.setText("")
                txtTotalVenta.setText("")
            }else{
                Toast.makeText(this, "Campos vacíos", Toast.LENGTH_SHORT).show()
            }
        }

    }

}