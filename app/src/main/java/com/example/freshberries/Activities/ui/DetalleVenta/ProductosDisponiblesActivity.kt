package com.example.freshberries.Activities.ui.DetalleVenta

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.R

class ProductosDisponiblesActivity : AppCompatActivity() {

    private lateinit var bd: FreshBerriesBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_productos_disponibles)

        var id : String? = ""
        var noNullid : String = ""
        val intent = intent

        if (intent.hasExtra("id_venta")){

            //Se guarda el valor envíado en una variable para trabajar con ella el método de buscar en el DAO
            id = intent.getStringExtra("id_venta")
            noNullid = id.orEmpty()
        }

        val btnFinalizar = findViewById<Button>(R.id.btnFinalizar)

        val recyclerView = findViewById<RecyclerView>(R.id.listRecycleViewProductos)
        val adapter = ProductosAdapter(application, noNullid)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        //Instancia de la Base de datos
        bd = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()

        btnFinalizar.setOnClickListener {
            val nextActivity = AddProductoActivity::class.java
            val intent = Intent(this, nextActivity)
            intent.putExtra("id_venta", bd.ventaDAO.obtenerUltimaVentaRegistrada().toString())
            startActivity(intent)
        }

    }
}