package com.example.dosificapp.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.dosificapp.R
import com.example.dosificapp.data.LoginRepository
import com.example.dosificapp.databinding.FragmentAcompBinding
import com.example.dosificapp.service.UserService
import com.example.dosificapp.ui.main.adapters.UserListAdapter
import kotlinx.coroutines.launch

class AcompaFragment : AbstractFragment() {

    private lateinit var binding: FragmentAcompBinding
    private val userService = UserService()
    private val loginRepository = LoginRepository.getInstance()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentAcompBinding.inflate(inflater, container, false)
        fetchAcompaniados()
        return binding.root
    }

    private fun fetchAcompaniados() {
        lifecycleScope.launch {
            try {
                val userId = loginRepository.loggedInUser?.id ?: return@launch
                val acompList = userService.getAcompaniados(userId)
                val adapter = UserListAdapter(requireContext(), R.layout.listview_acomp, acompList)
                binding.listAcomp.adapter = adapter
            } catch (e: Exception) {
                Log.d("ERROR", e.message!!)
            }
        }
    }

    override fun getName(): String = "USUARIOS ACOMPAÑADOS"

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(index: Int) = AcompaFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_SECTION_NUMBER, index)
            }
        }
    }
}
