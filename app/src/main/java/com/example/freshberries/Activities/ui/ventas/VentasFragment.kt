package com.example.freshberries.Activities.ui.ventas

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
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.Modelo.Venta
import com.example.freshberries.databinding.FragmentVentasBinding


class VentasFragment : Fragment() {

    private lateinit var bd: FreshBerriesBD

    private var _binding: FragmentVentasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val clientesViewModel =
            ViewModelProvider(this).get(VentasViewModel::class.java)

        _binding = FragmentVentasBinding.inflate(inflater, container, false)
        val root: View = binding.root


        clientesViewModel.text.observe(viewLifecycleOwner) {

        }
        val txtIdVenta : EditText = binding.txtIdVenta

        val btnRegistrarVenta : Button = binding.btnRegistrarVenta
        val btnBuscarVenta : Button = binding.btnBuscarVenta
        val btnListarVentas : Button = binding.btnListarVentas

        //Instancia de la Base de datos
        bd = Room.databaseBuilder(requireContext(), FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()

        btnRegistrarVenta.setOnClickListener {
            val nextActivity = RegistrarVentaActivity::class.java
            val intent = Intent(requireContext(), nextActivity)
            startActivity(intent)
        }

        btnBuscarVenta.setOnClickListener {
            if (txtIdVenta.text.toString().isNotEmpty()){
                val id : String = txtIdVenta.text.toString()

                if(id != null){
                    val busqueda : Venta? = bd.ventaDAO.buscarVenta(id.toLong())
                    if (busqueda != null){
                        val nextActivity = BuscarVentaActivity::class.java
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
        btnListarVentas.setOnClickListener {
            val nextActivity = ListarVentasActivity::class.java
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