package com.example.freshberries.Activities.ui.usuario

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
import com.example.freshberries.Activities.ui.ventas.BuscarVentaActivity
import com.example.freshberries.Activities.ui.ventas.ListarVentasActivity
import com.example.freshberries.Activities.ui.ventas.RegistrarVentaActivity
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.Modelo.Cliente
import com.example.freshberries.Modelo.Producto
import com.example.freshberries.Modelo.Usuario
import com.example.freshberries.Modelo.Venta
import com.example.freshberries.R
import com.example.freshberries.databinding.FragmentClientesBinding
import com.example.freshberries.databinding.FragmentProductoBinding
import com.example.freshberries.databinding.FragmentUsuarioBinding

class UsuarioFragment : Fragment() {

    private lateinit var bd: FreshBerriesBD

    private var _binding: FragmentUsuarioBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val usuarioViewModel =
            ViewModelProvider(this).get(UsuarioViewModel::class.java)

        _binding = FragmentUsuarioBinding.inflate(inflater, container, false)
        val root: View = binding.root


        usuarioViewModel.text.observe(viewLifecycleOwner) {

        }

        val txtId : EditText = binding.txtIdUser

        val btnRegistrarUsuario : Button = binding.btnAgregarUsuario
        val btnBuscarUsuario= binding.btnBuscarUsuarios
        val btnListarUsuarios : Button = binding.btnListarUsuarios

        //Instancia de la Base de datos
        bd = Room.databaseBuilder(requireContext(), FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()

        btnRegistrarUsuario.setOnClickListener {
            val nextActivity = RegistrarUsuarioActivity::class.java
            val intent = Intent(requireContext(), nextActivity)
            startActivity(intent)
        }

        btnBuscarUsuario.setOnClickListener {
            if (txtId.text.toString().isNotEmpty()){
                val id : String = txtId.text.toString()

                if(id != null){
                    val busqueda : Usuario? = bd.usuarioDAO.buscarUsuario(id.toLong())
                    if (busqueda != null){
                        val nextActivity = BuscarUsuarioActivity::class.java
                        val intent = Intent(requireContext(), nextActivity)

                        // Se envía el ID ingesado por el usuario a traves del intent al BuscarProveedorActivity y trabajar con este dato desde ahí
                        intent.putExtra("id", id)
                        startActivity(intent)
                    }else{
                        Toast.makeText(requireContext(), "No existen ventas con ID ${id}", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(requireContext(), "Ingrese el ID del la venta", Toast.LENGTH_SHORT).show()
            }
        }
        btnListarUsuarios.setOnClickListener {
            val nextActivity = ListarUsuarioActivity::class.java
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