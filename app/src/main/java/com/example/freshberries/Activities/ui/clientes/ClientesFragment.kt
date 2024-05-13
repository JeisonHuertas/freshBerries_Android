package com.example.freshberries.Activities.ui.clientes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.freshberries.Activities.ui.proveedores.BuscarProveedorActivity
import com.example.freshberries.Activities.ui.proveedores.RegistrarProveedorActivity
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.Modelo.Cliente
import com.example.freshberries.Modelo.Proveedor
import com.example.freshberries.databinding.ActivityListarClienteBinding
import com.example.freshberries.databinding.FragmentClientesBinding

class ClientesFragment : Fragment() {

    private lateinit var bd: FreshBerriesBD

    private var _binding: FragmentClientesBinding? = null

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

        _binding = FragmentClientesBinding.inflate(inflater, container, false)
        val root: View = binding.root


        clientesViewModel.text.observe(viewLifecycleOwner) {

        }
        val txtIdCliente : EditText = binding.txtIdCliente

        val btnRegistrarCliente : Button = binding.btnRegistrarCliente
        val btnBuscarCliente : Button = binding.btnBuscarCliente
        val btnListarCliente : Button = binding.btnListarCliente

        //Instancia de la Base de datos
        bd = Room.databaseBuilder(requireContext(), FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()

        btnRegistrarCliente.setOnClickListener {
            val nextActivity = ActivityRegistrarCliente::class.java
            val intent = Intent(requireContext(), nextActivity)
            startActivity(intent)
        }

        btnBuscarCliente.setOnClickListener {
            if (txtIdCliente.text.toString().isNotEmpty()){
                val id : String = txtIdCliente.text.toString()

                if(id != null){
                    val busqueda : Cliente? = bd.clienteDAO.buscarCliente(id.toLong())
                    if (busqueda != null){
                        val nextActivity = BuscarProveedorActivity::class.java
                        val intent = Intent(requireContext(), nextActivity)

                        // Se envía el ID ingesado por el usuario a traves del intent al BuscarProveedorActivity y trabajar con este dato desde ahí
                        intent.putExtra("id", id)
                        startActivity(intent)
                    }else{
                        Toast.makeText(requireContext(), "No existe el cliente con ID ${id}", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(requireContext(), "Ingrese el ID del cliente", Toast.LENGTH_SHORT).show()
            }
        }
        btnListarCliente.setOnClickListener {
            val nextActivity = ListarClienteActivity::class.java
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