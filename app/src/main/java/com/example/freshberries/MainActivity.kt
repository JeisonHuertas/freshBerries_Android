package com.example.freshberries

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.Modelo.Usuario

class MainActivity : AppCompatActivity() {

    private lateinit var bd: FreshBerriesBD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nextActivity = MainActivity::class.java
        val intent = Intent(this, nextActivity)


        val txtUsuario = findViewById<EditText>(R.id.txtUsuario)
        val txtContrasena = findViewById<EditText>(R.id.txtContraseña)

        val btnIniciarSesion = findViewById<Button>(R.id.btnLogIn)

        bd = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()

        //Registro Usuario Admin por defecto
        val usuario:Usuario = Usuario("Administrador","Administrador","admin123","Administradores")
        bd.usuarioDAO.registrarUsuario(usuario)

        btnIniciarSesion.setOnClickListener {

            val usuario : String = txtUsuario.text.toString()
            val contrasena : String = txtContrasena.text.toString()

            if (usuario.isNotEmpty() && contrasena.isNotEmpty()){
                val busqueda : Usuario = bd.usuarioDAO.buscarUsuarioPorUser(usuario)
                if (busqueda != null){
                    if (busqueda.contrasena == contrasena){
                        startActivity(intent)
                        Toast.makeText(this,"Bienvenido ${usuario}", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"Los datos de ingresados son erróneos", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this,"No se encontró el usuario indicado", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Por favor complete los campos", Toast.LENGTH_SHORT).show()
            }


        }
    }
}