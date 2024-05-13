package com.example.freshberries.Activities.ui.clientes

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
import com.example.freshberries.Modelo.Cliente
import com.example.freshberries.Modelo.Proveedor
import com.example.freshberries.R

class ActivityRegistrarCliente : AppCompatActivity() {

    private lateinit var bd: FreshBerriesBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar_cliente)

        val txtId = findViewById<EditText>(R.id.txtIdCliente)
        val txtNombre = findViewById<EditText>(R.id.txtNombreCliente)
        val txtTelefono = findViewById<EditText>(R.id.txtTelefonoCliente)
        val txtDireccion = findViewById<EditText>(R.id.txtDireccionCliente)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        //Instancia de la Base de datos
        bd = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()

        btnRegistrar.setOnClickListener {
            val id = txtId.text.toString().toLongOrNull()
            val nombre = txtNombre.text.toString()
            val telefono = txtTelefono.text.toString()
            val direccion = txtDireccion.text.toString()

            if (id != null && nombre.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty()){
                val cliente = Cliente(id,nombre,telefono,direccion)
                try {
                    bd.clienteDAO.registrarCliente(cliente)
                }catch (ex : Exception){
                    Toast.makeText(this, "Ya existe un cliente con ese ID", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this, "Se registró el cliente", Toast.LENGTH_SHORT).show()

                txtId.setText("")
                txtTelefono.setText("")
                txtDireccion.setText("")
                txtNombre.setText("")
            }else{
                Toast.makeText(this, "Campos vacíos", Toast.LENGTH_SHORT).show()
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}