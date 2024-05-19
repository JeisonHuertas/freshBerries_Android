package com.example.freshberries.Activities.ui.usuario

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.R

class ListarUsuarioActivity : AppCompatActivity() {
    private lateinit var db: FreshBerriesBD
    private lateinit var tabla: TableLayout
    private lateinit var tabla2: TableLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listar_usuario)

        tabla = findViewById<TableLayout>(R.id.tabla)



        db = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME)
            .allowMainThreadQueries().build()


        val usuarios = db.usuarioDAO.obtenerUsuarios()




        for (usuario in usuarios) {
            val fila = TableRow(this)

            val textViewCodigo = TextView(this).apply { text = usuario.id.toString() }
            val textViewNombre = TextView(this).apply { text = usuario.nombre }
            val textViewUsuario = TextView(this).apply { text = usuario.usuario }
            val textViewContrasena = TextView(this).apply { text = usuario.contrasena }
            val textViewPerfil  = TextView(this).apply { text = usuario.perfil }



            fila.addView(textViewCodigo)
            fila.addView(textViewNombre)
            fila.addView(textViewUsuario)
            fila.addView(textViewContrasena)
            fila.addView(textViewPerfil)




            tabla.addView(fila)





        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.listRecycleViewProductos)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}