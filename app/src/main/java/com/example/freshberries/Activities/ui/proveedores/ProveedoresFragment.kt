package com.example.freshberries.Activities.ui.proveedores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.freshberries.databinding.FragmentProveedoresBinding

class ProveedoresFragment : Fragment() {

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
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}