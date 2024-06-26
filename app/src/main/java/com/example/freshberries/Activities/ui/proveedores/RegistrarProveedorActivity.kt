package com.example.freshberries.Activities.ui.proveedores

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.Modelo.Proveedor
import com.example.freshberries.R

class RegistrarProveedorActivity : AppCompatActivity() {

    private lateinit var bd: FreshBerriesBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar_proveedor)

        val txtId = findViewById<EditText>(R.id.txtIdProveedor)
        val txtNombre = findViewById<EditText>(R.id.txtNombreProveedor)
        val txtTelefono = findViewById<EditText>(R.id.txtTelefonoProveedor)
        val txtDireccion = findViewById<EditText>(R.id.txtDireccionProveedor)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrarProveedor)

        //Instancia de la Base de datos
        bd = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()

        btnRegistrar.setOnClickListener {
            val id = txtId.text.toString().toLongOrNull()
            val nombre = txtNombre.text.toString()
            val telefono = txtTelefono.text.toString()
            val direccion = txtDireccion.text.toString()

            if (id != null && nombre.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty()){
                val proveedor = Proveedor(id,nombre,telefono,direccion)
                try {
                    bd.proveedorDAO.registrarProveedor(proveedor)
                }catch (ex : Exception){
                    Toast.makeText(this, "Ya existe un proveedor con ese ID", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this, "Se registró el proveedor", Toast.LENGTH_SHORT).show()

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