package com.example.freshberries.Activities.ui.ventas

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freshberries.Activities.MainActivityAdministrador
import com.example.freshberries.R

class ListarVentasActivity : AppCompatActivity() {

    override fun onBackPressed() {
        val nextActivity = MainActivityAdministrador::class.java
        val intent = Intent(this, nextActivity)
        startActivity(intent)

        // To prevent the default back button behavior, comment out the next line
        super.onBackPressed()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listar_ventas)

        val recyclerViewVentas = findViewById<RecyclerView>(R.id.recyclerViewVentas)
        val adapter = VentasAdapter(application)

        recyclerViewVentas.layoutManager = LinearLayoutManager(this)
        recyclerViewVentas.adapter = adapter


    }
}