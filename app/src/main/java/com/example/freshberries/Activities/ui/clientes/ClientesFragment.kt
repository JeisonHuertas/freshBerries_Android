package com.example.freshberries.Activities.ui.clientes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.freshberries.databinding.FragmentClientesBinding

class ClientesFragment : Fragment() {

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
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}