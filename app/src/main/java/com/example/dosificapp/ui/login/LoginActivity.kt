package com.example.dosificapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.dosificapp.CreateAccount
import com.example.dosificapp.MainActivity
import com.example.dosificapp.data.LoginRepository
import com.example.dosificapp.databinding.ActivityLoginBinding
import com.example.dosificapp.dominio.Usuario
import com.example.dosificapp.service.UserService
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private val loginRepository = LoginRepository.getInstance()
    private val userService = UserService()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (loginRepository.isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            val email = binding.username.text.toString()
            val password = binding.password.text.toString()
            loginUser(email, password)
        }

        binding.register?.setOnClickListener {
            startActivity(Intent(this, CreateAccount::class.java))
        }
    }

    private fun loginUser(email: String, password: String) {
        lifecycleScope.launch {
            try {
                val loginSuccess = userService.login(email, password)
                if (loginSuccess) {
                    // Handle successful login
                    val user = Usuario(email, password) // Adjust as needed
                    loginRepository.setLoggedInUser(user, applicationContext)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else {
                    Toast.makeText(applicationContext, "Error al iniciar sesion", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Error al iniciar sesion", Toast.LENGTH_SHORT).show()
            } finally {
                binding.loading.visibility = View.GONE
            }
        }
    }

    override fun onBackPressed() {}
}