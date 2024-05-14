package com.example.freshberries.Activities.ui.producto

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.freshberries.Activities.ui.clientes.ActivityRegistrarCliente
import com.example.freshberries.Activities.ui.clientes.ClientesViewModel
import com.example.freshberries.Activities.ui.clientes.ListarClienteActivity
import com.example.freshberries.Activities.ui.proveedores.BuscarProveedorActivity
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.Modelo.Cliente
import com.example.freshberries.Modelo.Producto
import com.example.freshberries.R
import com.example.freshberries.databinding.FragmentClientesBinding
import com.example.freshberries.databinding.FragmentProductoBinding

class ProductoFragment : Fragment() {

    private lateinit var bd: FreshBerriesBD

    private var _binding: FragmentProductoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val clientesViewModel =
            ViewModelProvider(this).get(ClientesViewModel::class.java)

        _binding = FragmentProductoBinding.inflate(inflater, container, false)
        val root: View = binding.root


        clientesViewModel.text.observe(viewLifecycleOwner) {

        }
        val txtIdProducto : EditText = binding.txtIdProducto

        val btnRegistrarProducto : Button = binding.btnRegistrar
        val btnBuscarProducto : Button = binding.btnBuscarProducto
        val btnListarProducto : Button = binding.btnListarProducto


        //Instancia de la Base de datos
        bd = Room.databaseBuilder(requireContext(), FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()

        btnRegistrarProducto.setOnClickListener {
            val nextActivity = RegistrarProductoActivity::class.java
            val intent = Intent(requireContext(), nextActivity)
            startActivity(intent)
        }

        btnBuscarProducto.setOnClickListener {
            if (txtIdProducto.text.toString().isNotEmpty()){
                val id : String = txtIdProducto.text.toString()

                if(id != null){
                    val busqueda : Producto? = bd.productoDAO.buscarProducto(id.toLong())
                    if (busqueda != null){
                        val nextActivity = BuscarProductoActivity::class.java
                        val intent = Intent(requireContext(), nextActivity)


                        intent.putExtra("id", id)
                        startActivity(intent)
                    }else{
                        Toast.makeText(requireContext(), "No existe el producto con ID ${id}", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(requireContext(), "Ingrese el ID del producto", Toast.LENGTH_SHORT).show()
            }
        }
        btnListarProducto.setOnClickListener {
            val nextActivity = ListarProductoActivity::class.java
            val intent = Intent(requireContext(), nextActivity)
            startActivity(intent)

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}