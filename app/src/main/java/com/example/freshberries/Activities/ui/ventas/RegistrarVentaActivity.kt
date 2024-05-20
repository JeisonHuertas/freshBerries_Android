package com.example.freshberries.Activities.ui.ventas

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.freshberries.Activities.ui.DetalleVenta.AddProductoActivity
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.Modelo.Cliente
import com.example.freshberries.Modelo.Usuario
import com.example.freshberries.Modelo.Venta
import com.example.freshberries.R
import java.util.Calendar
import java.util.Date

class RegistrarVentaActivity : AppCompatActivity() {

    private lateinit var bd: FreshBerriesBD


    override fun onBackPressed() {
        val nextActivity = ListarVentasActivity::class.java
        val intent = Intent(this, nextActivity)
        startActivity(intent)

        // To prevent the default back button behavior, comment out the next line
        super.onBackPressed()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar_venta)

        //val txtIdEmpleado = findViewById<EditText>(R.id.txtIdEmpleado)
        //val txtIdCliente = findViewById<EditText>(R.id.txtClienteId)
        val txtTotalVenta = findViewById<EditText>(R.id.txtTotalVenta)
        val txtFechaVenta = findViewById<EditText>(R.id.txtFechaVenta)
        var IdEmpleado : String = ""
        var IdCliente : String = ""
        val spinnerEmpleados = findViewById<Spinner>(R.id.spnEmpleados)
        val spinnerClientes = findViewById<Spinner>(R.id.spnCliente)


        val btnAddProducto = findViewById<Button>(R.id.btnAddProducto)

        var fechaSeleccionada : Date = Date()

        //Instancia de la Base de datos
        bd = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()


        var empleadosList =bd.usuarioDAO.obtenerUsuarios()

        var clientesList = bd.clienteDAO.obtenerClientes()

        val usuarioAdapter = object : ArrayAdapter<Usuario>(
            this,
            android.R.layout.simple_spinner_item, // or your custom layout resource
            empleadosList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val usuario = getItem(position)
                val textView = (convertView ?: LayoutInflater.from(context)
                    .inflate(R.layout.spinner_dropdown_item, parent, false)) as TextView
                if (usuario != null) {

                    val text = "<b>${usuario.id}</b> ${usuario.nombre}"
                    IdEmpleado = usuario.id.toString()
                    textView.text = Html.fromHtml(text)

                }
                return textView
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                return getView(position, convertView, parent)
            }
        }

        spinnerEmpleados.adapter = usuarioAdapter


        ///////////////////////////////////////////////////////////////////////////////////////////////
        val clientesAdapter = object : ArrayAdapter<Cliente>(
            this,
            android.R.layout.simple_spinner_item, // or your custom layout resource
            clientesList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val cliente = getItem(position)
                val textView = (convertView ?: LayoutInflater.from(context)
                    .inflate(R.layout.spinner_dropdown_item, parent, false)) as TextView
                if (cliente != null) {

                    val text = "<b>${cliente.id}</b> ${cliente.nombre}"
                    IdCliente = cliente.id.toString()
                    textView.text = Html.fromHtml(text)

                }
                return textView
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                return getView(position, convertView, parent)
            }
        }

        spinnerClientes.adapter = clientesAdapter


        ////////////////////////////////////////////////////////////////////////////////////////////////


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



        btnAddProducto.setOnClickListener {


            val fechaVenta = fechaSeleccionada
            val empleadoId = IdEmpleado   //txtIdEmpleado.text.toString()
            val clienteId =  IdCliente  //txtIdCliente.text.toString()
            val totalVenta = 0


            if (empleadoId.isNotEmpty() && clienteId.isNotEmpty()){
                val venta = Venta(fechaVenta,empleadoId.toLong(),clienteId.toLong(), totalVenta.toLong())
                try {
                    bd.ventaDAO.registrarVenta(venta)
                }catch (ex : Exception){
                    Toast.makeText(this, "Ya existe una venta con ese ID", Toast.LENGTH_SHORT).show()
                }

                val nextActivity = AddProductoActivity::class.java
                val intent = Intent(this, nextActivity)
                intent.putExtra("id_venta", bd.ventaDAO.obtenerUltimaVentaRegistrada().toString() )
                startActivity(intent)

            }else{
                Toast.makeText(this, "Campos vacíos", Toast.LENGTH_SHORT).show()
            }

        }

    }

}