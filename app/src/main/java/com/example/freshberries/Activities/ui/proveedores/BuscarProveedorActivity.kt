package com.example.freshberries.Activities.ui.proveedores

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
import com.example.freshberries.R

class BuscarProveedorActivity : AppCompatActivity() {

    private lateinit var bd: FreshBerriesBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar_proveedor)


        val txtId = findViewById<EditText>(R.id.txtIdProveedor)
        val txtNombre = findViewById<EditText>(R.id.txtNombreProveedor)
        val txtTelefono = findViewById<EditText>(R.id.txtTelefonoProveedor)
        val txtDireccion = findViewById<EditText>(R.id.txtDireccionProveedor)

        val btnActualizar = findViewById<Button>(R.id.btnActualizarProveedor)

        var id : String? = ""
        val intent = intent

        if (intent.hasExtra("id")){
            id = intent.getStringExtra("id")
        }

        //Instancia de la Base de datos
        bd = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()


        if(id != null) {
            val busqueda: Proveedor? = bd.proveedorDAO.buscarProveedor(id.toLong())
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
                val proveedor = Proveedor(id,nombre,telefono,direccion)
                try {
                    bd.proveedorDAO.actualizarProveedor(proveedor)
                }catch (ex : Exception){
                    Toast.makeText(this, "Error : ${ex}", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this, "Se actualizó el proveedor", Toast.LENGTH_SHORT).show()

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