package com.example.dosificapp.service

import io.ktor.client.HttpClient

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.HttpStatusCode

class DosesService {

    private val client = HttpClient()
    private val baseUrl = "YOUR_BASE_URL"

    suspend fun confirmTomaDosis(dosisId: Long): Boolean {
        return postRequest("ConfirmarTomaDosis", dosisId)
    }

    suspend fun postergarNotificacionDosis(dosisId: Long): Boolean {
        return postRequest("PostergarNotificacionDosis", dosisId)
    }

    suspend fun actualizarNotificacionDosis(dosisId: Long): Boolean {
        return postRequest("ActualizarNotificacionDosis", dosisId)
    }

    private suspend fun postRequest(endpoint: String, dosisId: Long): Boolean {
        val url = "$baseUrl/api/PacienteAcompaniante/$endpoint/$dosisId"

        return try {
            val response: HttpResponse = client.post(url)
            return response.status == HttpStatusCode.OK
        } catch (e: Exception) {
            println("Error: ${e.message}")
            false
        }
    }
}
