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

class BuscarClienteActivity : AppCompatActivity() {

    private lateinit var bd: FreshBerriesBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar_cliente)

        val txtId = findViewById<EditText>(R.id.txtIdCliente)
        val txtNombre = findViewById<EditText>(R.id.txtNombreCliente)
        val txtTelefono = findViewById<EditText>(R.id.txtTelefonoCliente)
        val txtDireccion = findViewById<EditText>(R.id.txtDireccionCliente)

        val btnActualizar = findViewById<Button>(R.id.btnActualizarCliente)

        var id : String? = ""
        val intent = intent


        if (intent.hasExtra("id")){


            id = intent.getStringExtra("id")
        }

        //Instancia de la Base de datos
        bd = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()


        if(id != null) {
            val busqueda: Cliente? = bd.clienteDAO.buscarCliente(id.toLong())
            if (busqueda != null) {
                txtId.setText(busqueda.id.toString())
                txtTelefono.setText(busqueda.telefono)
                txtDireccion.setText(busqueda.direccion)
                txtNombre.setText(busqueda.nombre)
            }
        }

        btnActualizar.setOnClickListener {

            val id = txtId.text.toString().toLongOrNull()
            val nombre = txtNombre.text.toString()
            val telefono = txtTelefono.text.toString()
            val direccion = txtDireccion.text.toString()

            if (id != null && nombre.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty()){
                val cliente = Cliente(id,nombre,telefono,direccion)
                try {
                    bd.clienteDAO.actualizarCliente(cliente)
                }catch (ex : Exception){
                    Toast.makeText(this, "Error : ${ex}", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this, "Se actualizó el cliente", Toast.LENGTH_SHORT).show()

                txtId.setText("")
                txtTelefono.setText("")
                txtDireccion.setText("")
                txtNombre.setText("")
            }else{
                Toast.makeText(this, "Campos vacíos", Toast.LENGTH_SHORT).show()
            }
        }

    }

}
