package com.example.freshberries.Activities.ui.DetalleVenta

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.freshberries.Activities.ui.ventas.RegistrarVentaActivity
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.R

class AddProductoActivity : AppCompatActivity() {

    private lateinit var db: FreshBerriesBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_producto)


        var id : String? = ""
        val intent = intent

        //Se valida que el dato envíado desde el ProveedoresFragmet tenga un valor con la llave id
        if (intent.hasExtra("id_venta")){

            //Se guarda el valor envíado en una variable para trabajar con ella el método de buscar en el DAO
            id = intent.getStringExtra("id_venta")
        }

        db = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()


        val btnAddProducto = findViewById<Button>(R.id.btnAnadirProducto)
        val txtViewTotal = findViewById<TextView>(R.id.txtTotal)
        val btnFinalizarVenta = findViewById<Button>(R.id.btnFinalizarVenta)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewDetalles)
        val adapter = DetallesAdapter(application)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        if (id != null) {
            txtViewTotal.text = actualizarTotal(id.toLong()).toString()
        }

        btnAddProducto.setOnClickListener {
            val nextActivity = ProductosDisponiblesActivity::class.java
            val intent = Intent(this, nextActivity)
            intent.putExtra("id_venta", id)

            startActivity(intent)
            finish()
        }

        btnFinalizarVenta.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            val nextActivity = RegistrarVentaActivity::class.java
            val intent = Intent(this, nextActivity)
            val total = txtViewTotal.text.toString().toLong()


            if (db.detalleVentaDAO.obtenerDetalle_ventasPorIdVenta(db.ventaDAO.obtenerUltimaVentaRegistrada()).size > 0) {

                db.ventaDAO.actualizarTotal(total,db.ventaDAO.obtenerUltimaVentaRegistrada())
                with(builder) {
                    setTitle("Registro Venta")
                    setMessage("Se ha Registrado la venta exitosamente")
                    setPositiveButton("Aceptar") { dialog, _ ->
                        startActivity(intent)
                        finish()
                    }
                    show()
                }


            }else{
                with(builder) {
                    setTitle("Error")
                    setMessage("Por favor añada al menos un elemento a la venta")
                    setPositiveButton("Aceptar") { dialog, _ ->
                    }
                    show()
                }
            }

        }
    }

    fun actualizarTotal(id_venta : Long): Long{

        var TotalVenta : Long = 0

        var detalles = db.detalleVentaDAO.obtenerDetalle_ventasPorIdVenta(id_venta)

        for (detalle in detalles){
            TotalVenta += detalle.precio_venta
        }
        return  TotalVenta
    }

}