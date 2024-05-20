package com.example.freshberries

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.freshberries.Activities.MainActivityAdministrador
import com.example.freshberries.Activities.MainActivityEmpleados
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.Modelo.Usuario

class MainActivity : AppCompatActivity() {

    private lateinit var bd: FreshBerriesBD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.listRecycleViewProductos)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Variable que contiene el ActivityLiberado al usuario con rol de Administrador
        val nextActivity = MainActivityAdministrador::class.java
        val intent = Intent(this, nextActivity)


        val txtUsuario = findViewById<EditText>(R.id.txtUsuario)
        val txtContrasena = findViewById<EditText>(R.id.txtContraseña)

        val btnIniciarSesion = findViewById<Button>(R.id.btnLogIn)

        //Instancia de la Base de datos
        bd = Room.databaseBuilder(
            application,
            FreshBerriesBD::class.java,
            FreshBerriesBD.DATABASE_NAME
        ).allowMainThreadQueries().build()

        //Registro Usuario Admin por defecto
        //val usuario: Usuario = Usuario("Administrador78", "Administrador78", "admin123", "Administrador")
        //bd.usuarioDAO.registrarUsuario(usuario)

        btnIniciarSesion.setOnClickListener {

            val usuario: String = txtUsuario.text.toString()
            val contrasena: String = txtContrasena.text.toString()


            //Validar que los campos se encuentren editados por el usuario
            if (usuario.isNotEmpty() && contrasena.isNotEmpty()) {

                //Resultado de buscar el usuario ingresado en el campo de texto
                val busqueda: Usuario = bd.usuarioDAO.buscarUsuarioPorUser(usuario)



                if (busqueda != null) {

                    //validación de credeciales entre las inrgesadas y la guardada en la BD
                    if (busqueda.contrasena == contrasena) {
                        val perfil = busqueda.perfil

                        if (perfil == "Administrador") {

                            startActivity(Intent(this, MainActivityAdministrador::class.java))
                            Toast.makeText(
                                this,
                                "Bienvenido Administrador ${usuario}",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (perfil == "Empleado") {

                            startActivity(Intent(this, MainActivityEmpleados::class.java))

                            Toast.makeText(this, "Bienvenido ${usuario}", Toast.LENGTH_LONG).show()

                        } else {
                            Toast.makeText(
                                this,
                                "Los datos de ingresados son erróneos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        Toast.makeText(
                            this,
                            "Contraseña incorrecta",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(this, "No se encuentra el usuario ${usuario}", Toast.LENGTH_LONG).show()
                }


            }
        }
    }
}