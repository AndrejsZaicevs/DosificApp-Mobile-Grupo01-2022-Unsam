package com.example.dosificapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.dosificapp.data.LoginRepository
import com.example.dosificapp.databinding.ActivityPerfilBinding
import com.example.dosificapp.dominio.Usuario
import com.example.dosificapp.service.UserService
import com.example.dosificapp.ui.main.adapters.UserListAdapter
import kotlinx.coroutines.launch
import java.util.Base64

class PerfilActivity : AppCompatActivity() {
    private lateinit var loginRepository: LoginRepository
    private lateinit var userService: UserService
    private lateinit var binding: ActivityPerfilBinding
    private val listaAcomp: ArrayList<Usuario> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginRepository = LoginRepository.getInstance()

        val user = loginRepository.loggedInUser

        user?.let {
            if (it.imageBase64.isNotEmpty()) {
                val imageBytes = Base64.getDecoder().decode(it.imageBase64)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                binding.imageView.setImageBitmap(Bitmap.createScaledBitmap(decodedImage, 120, 120, false))
            }

            binding.profileName.setText(it.nombre)
            binding.profileLastName.setText(it.apellido)
            binding.profileEmail.setText(it.email)
            binding.profilePhone.setText(it.numero)
        }

        binding.buttonSalir.setOnClickListener { finish() }
        binding.buttonGuardar.setOnClickListener { enviarUser() }

        loadListaAcompaniantes()
    }

    private fun verificarCampos(): Boolean {
        return binding.profileName.text.isNotEmpty() &&
                binding.profileLastName.text.isNotEmpty() &&
                binding.profileEmail.text.isNotEmpty() &&
                binding.profilePhone.text.isNotEmpty()
    }

    private fun enviarUser() {
        if (!verificarCampos()) {
            Toast.makeText(this, "Verifique que no haya campos vacios", Toast.LENGTH_SHORT).show()
        }
        // Add any logic to send the user data if needed
    }

    private fun loadListaAcompaniantes() {
        lifecycleScope.launch {
            val userId = loginRepository.loggedInUser?.id ?: return@launch
            val users = userService.getListaAcompaniantes(userId.toString())
            listaAcomp.clear()
            listaAcomp.addAll(users)

            val adapter = UserListAdapter(this@PerfilActivity, R.layout.listview_acomp, listaAcomp)
            binding.listAcomp.adapter = adapter
        }
    }
}