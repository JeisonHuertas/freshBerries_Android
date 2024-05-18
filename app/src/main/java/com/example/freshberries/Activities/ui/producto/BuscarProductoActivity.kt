package com.example.freshberries.Activities.ui.producto

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.Modelo.Producto
import com.example.freshberries.R

class BuscarProductoActivity : AppCompatActivity() {
    private lateinit var bd: FreshBerriesBD
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar_producto)

        val txtId = findViewById<EditText>(R.id.txtIdProducto)
        val txtNombre = findViewById<EditText>(R.id.txtNombreProducto)
        val txtDescripcion = findViewById<EditText>(R.id.txtDescripcion)
        val txtPrecioCompra = findViewById<EditText>(R.id.txtPrecioCompra)
        val txtPrecioVenta = findViewById<EditText>(R.id.txtPrecioVenta)
        val txtStock = findViewById<EditText>(R.id.txtStock)
        val txtIdProveedor = findViewById<EditText>(R.id.txtProveedorId)

        val btnActualizar = findViewById<Button>(R.id.RegistrarProducto)

        var id : String? = ""
        val intent = intent


        if (intent.hasExtra("id")){

            //Se guarda el valor envíado en una variable para trabajar con ella el método de buscar en el DAO
            id = intent.getStringExtra("id")
        }

        //Instancia de la Base de datos
        bd = Room.databaseBuilder(application, FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()


        if(id != null) {
            val busqueda: Producto? = bd.productoDAO.buscarProducto(id.toLong())
            if (busqueda != null) {
                txtId.setText(busqueda.id.toString())
                txtNombre.setText(busqueda.nombre)
                txtDescripcion.setText(busqueda.descripcion)
                txtPrecioCompra.setText(busqueda.precio_compra)
                txtPrecioVenta.setText(busqueda.precio_venta)
                txtStock.setText(busqueda.stock)
                txtIdProveedor.setText(busqueda.proveedor_id.toString())
            }
        }

        btnActualizar.setOnClickListener {

            val id = txtId.text.toString().toLongOrNull()
            val nombre = txtNombre.text.toString()
            val descripcion = txtDescripcion.text.toString()
            val precioCompra = txtPrecioCompra.text.toString().toIntOrNull()
            val precioVenta = txtPrecioVenta.text.toString().toIntOrNull()
            val Stock = txtStock.text.toString().toIntOrNull()
            val IDproveedor = txtIdProveedor.text.toString().toLongOrNull()

            if (id != null && nombre.isNotEmpty() && descripcion.isNotEmpty() && precioCompra !=null && precioVenta !=null
                && Stock !=null && IDproveedor !=null){
                val producto = Producto(id,nombre,descripcion,precioCompra, precioVenta, Stock, IDproveedor)
                try {
                    bd.productoDAO.actualizarProducto(producto)
                }catch (ex : Exception){
                    Toast.makeText(this, "Error : ${ex}", Toast.LENGTH_SHORT).show()
                }
                Toast.makeText(this, "Se actualizó el producto", Toast.LENGTH_SHORT).show()

                txtId.setText("")
                txtDescripcion.setText("")
                txtPrecioCompra.setText("")
                txtPrecioVenta.setText("")
                txtStock.setText("")
                txtIdProveedor.setText("")
            }else{
                Toast.makeText(this, "Campos vacíos", Toast.LENGTH_SHORT).show()
            }
        }

    }

}
