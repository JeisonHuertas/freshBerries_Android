package com.example.freshberries.Activities.ui.usuario

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
import com.example.freshberries.Modelo.Producto
import com.example.freshberries.Modelo.Usuario
import com.example.freshberries.R

class BuscarUsuarioActivity : AppCompatActivity() {

    private lateinit var bd: FreshBerriesBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar_usuario)

        val txtId = findViewById<EditText>(R.id.txtIdB)
        val txtNombre = findViewById<EditText>(R.id.txtNombreUser)
        val txtUser = findViewById<EditText>(R.id.txtUsuario)
        val txtContrasena = findViewById<EditText>(R.id.txtConstrasena)
        val txtRol = findViewById<EditText>(R.id.txtRol)



        val btnActualizar = findViewById<Button>(R.id.btnActualizarUser)

        var id : String? = ""
        val intent = intent


        if (intent.hasExtra("id")){

            //Se guarda el valor envíado en una variable para trabajar con ella el método de buscar en el DAO
            id = intent.getStringExtra("id")
        }

        //Instancia de la Base de datos
        bd = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()


        if(id != null) {
            val busqueda: Usuario? = bd.usuarioDAO.buscarUsuario(id.toLong())
            if (busqueda != null) {
                txtId.setText(busqueda.id.toString())
                txtNombre.setText(busqueda.nombre)
                txtUser.setText(busqueda.usuario)
                txtContrasena.setText(busqueda.contrasena)
                txtRol.setText(busqueda.perfil)

            }
        }

        btnActualizar.setOnClickListener {

            val id = txtId.text.toString().toLongOrNull()
            val nombre = txtNombre.text.toString()
            val usuario = txtUser.text.toString()
            val contrasena = txtContrasena.text.toString()
            val rol = txtRol.text.toString()


            if (id != null && nombre.isNotEmpty() && usuario.isNotEmpty() && contrasena.isNotEmpty()  && rol.isNotEmpty()){
                val user = Usuario(id,nombre,usuario, contrasena, rol)
                try {
                    bd.usuarioDAO.actualizarUsuario(user)
                }catch (ex : Exception){
                    Toast.makeText(this, "Error : ${ex}", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this, "Se actualizó el usuario", Toast.LENGTH_SHORT).show()

                txtId.setText("")
                txtNombre.setText("")
                txtContrasena.setText("")
                txtRol.setText("")

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