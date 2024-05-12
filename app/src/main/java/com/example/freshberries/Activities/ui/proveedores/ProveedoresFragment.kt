package com.example.freshberries.Activities.ui.proveedores

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.freshberries.Configuracion.FreshBerriesBD
import com.example.freshberries.databinding.FragmentProveedoresBinding

class ProveedoresFragment : Fragment() {

    private lateinit var bd: FreshBerriesBD

    private var _binding: FragmentProveedoresBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val proveedoresViewModel =
            ViewModelProvider(this).get(ProveedoresViewModel::class.java)

        _binding = FragmentProveedoresBinding.inflate(inflater, container, false)
        val root: View = binding.root


        proveedoresViewModel.text.observe(viewLifecycleOwner) {

        }

        val btnRegistrarProveedor : Button = binding.btnRegistrarProveedor

        //Instancia de la Base de datos
        bd = Room.databaseBuilder(requireContext(), FreshBerriesBD::class.java, FreshBerriesBD.DATABASE_NAME).allowMainThreadQueries().build()

        btnRegistrarProveedor.setOnClickListener {
            val nextActivity = RegistrarProveedorActivity::class.java
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