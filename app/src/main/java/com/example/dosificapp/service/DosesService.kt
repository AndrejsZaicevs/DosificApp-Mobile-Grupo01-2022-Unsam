package com.example.dosificapp.service

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.HttpStatusCode

class DosesService {

    private val client = HttpClient {
        install(JsonFeature){
            serializer = KotlinxSerializer()
        }
    }
    private val baseUrl = "http://192.168.0.104:3000"

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
