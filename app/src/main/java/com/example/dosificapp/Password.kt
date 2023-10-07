package com.example.dosificapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.dosificapp.databinding.ActivityPasswordBinding
import com.example.dosificapp.dominio.Usuario
import com.example.dosificapp.service.UserService
import kotlinx.coroutines.launch

class Password : AppCompatActivity() {
    private lateinit var binding: ActivityPasswordBinding
    private val userService = UserService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuario = intent.getSerializableExtra("user") as Usuario
        binding.textUsuario.text = usuario.nombreApellido

        binding.buttonContinuar.setOnClickListener {
            val pass = binding.editTextTextPassword.text.toString()
            val confirmPass = binding.editTextTextPasswordConfirm.text.toString()

            when {
                pass.isEmpty() || confirmPass.isEmpty() -> showToast("Complete los campos de contraseña")
                pass != confirmPass -> showToast("Las contraseñas no coinciden")
                else -> {
                    usuario.password = pass
                    lifecycleScope.launch {
                        userService.createUser(usuario)
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
