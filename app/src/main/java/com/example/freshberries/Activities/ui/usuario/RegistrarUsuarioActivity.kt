package com.example.freshberries.Activities.ui.usuario

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.Modelo.Usuario
import com.example.freshberries.R

class RegistrarUsuarioActivity : AppCompatActivity() {

    private lateinit var bd: FreshBerriesBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar_usuario)

        val txtId = findViewById<EditText>(R.id.txtIdB)
        val txtNombre = findViewById<EditText>(R.id.txtNombreUser)
        val txtCorreo = findViewById<EditText>(R.id.txtUsuario)
        val txtContrasena = findViewById<EditText>(R.id.txtConstrasena)
        val txtRadioBoton = findViewById<RadioGroup>(R.id.radioGroupSeleccion)
        val btnRadioBotonAdmin = findViewById<RadioButton>(R.id.radioBtnAdmin)
        val btnRadioBotonEmpleado = findViewById<RadioButton>(R.id.radioBtnUsuario)

        val btnRegistrarUsuario = findViewById<Button>(R.id.btnActualizarUser)

        //Instancia de la Base de datos
        bd = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()

        btnRegistrarUsuario.setOnClickListener {
            val id = txtId.text.toString().toLongOrNull()
            val nombre = txtNombre.text.toString()
            val correo = txtCorreo.text.toString()
            val contrasena = txtContrasena.text.toString()
            var perfil = " "

            if (btnRadioBotonAdmin.isChecked) {
                perfil = "Administrador"
            } else if (btnRadioBotonEmpleado.isChecked) {
                perfil = "Empleado"
            }else {
                Toast.makeText(this, "Seleccione un perfil Administrador o Empleado", Toast.LENGTH_SHORT).show()
            }

            if (id != null && nombre.isNotEmpty() && correo.isNotEmpty() && contrasena.isNotEmpty() && perfil.isNotEmpty()){
                val proveedor = Usuario(id,nombre,correo,contrasena, perfil)
                try {
                    bd.usuarioDAO.registrarUsuario(proveedor)
                }catch (ex : Exception){
                    Toast.makeText(this, "Ya existe un usuario con ese ID", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this, "Se registró el usuario", Toast.LENGTH_SHORT).show()

                txtId.setText("")
                txtNombre.setText("")
                txtCorreo.setText("")
                txtContrasena.setText("")

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