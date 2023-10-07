package com.example.dosificapp.service

import com.example.dosificapp.dominio.Dosis
import com.example.dosificapp.dominio.Usuario
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long

class UserService {

    private val client = HttpClient()
    private val baseUrl = "YOUR_BASE_URL"

    suspend fun createUser(user: Usuario): HttpResponse {
        val url = "$baseUrl/api/PacienteAcompaniante/CrearAcompaniante"
        val params = mapOf(
                "Nombre" to user.nombre,
                "Apellido" to user.apellido,
                "Email" to user.email,
                "Contrasenia" to user.password,
                "NumeroTel" to user.numero,
                "Documento" to user.documento,
                "TipoDocumento" to 1,
                "IdOrganizacion" to 1,
                "TipoUsuario" to 1
        )

        return client.put(url) {
            contentType(ContentType.Application.Json)
            body = params
        }
    }

    suspend fun login(user: String?, pass: String?): Boolean {
        val url = "$baseUrl/login"
        val params = mapOf(
                "username" to user,
                "password" to pass
        )

        val response: HttpResponse = client.post(url) {
            contentType(ContentType.Application.Json)
            body = params
        }

        return response.status == HttpStatusCode.OK
    }

    suspend fun getListaAcompaniantes(userId: String): List<Usuario> {
        val url = "$baseUrl/api/PacienteAcompaniante/ObtenerDatosPaciente/$userId"
        val response = client.get<String>(url)

        val listaAcomp = mutableListOf<Usuario>()

        val data = Json.parseToJsonElement(response).jsonObject
        val acomps = data["Acompaniantes"]?.jsonArray ?: return emptyList()

        for (acompElement in acomps) {
            val acomp = acompElement.jsonObject
            val user = Usuario().apply {
                nombre = acomp["Nombre"]?.jsonPrimitive?.content
                apellido = acomp["Apellido"]?.jsonPrimitive?.content
                email = acomp["Email"]?.jsonPrimitive?.content
            }
            listaAcomp.add(user)
        }

        return listaAcomp
    }

    suspend fun getAcompaniados(userId: Int): List<Usuario> {
        val url = "$baseUrl/api/PacienteAcompaniante/ObtenerPacientesAcompaniados/$userId"
        val response = client.get<String>(url)

        val listaAcomp = mutableListOf<Usuario>()
        val acomps = Json.parseToJsonElement(response).jsonArray

        for (acompElement in acomps) {
            val acomp = acompElement.jsonObject
            val user = Usuario().apply {
                nombre = acomp["Nombre"]?.jsonPrimitive?.content
                apellido = acomp["Apellido"]?.jsonPrimitive?.content
                email = acomp["Email"]?.jsonPrimitive?.content
            }
            listaAcomp.add(user)
        }

        return listaAcomp
    }

    suspend fun getDoses(userId: Int): List<Dosis> {
        val url = "$baseUrl/api/PacienteAcompaniante/ObtenerListadoDosis/$userId"
        val response = client.get<String>(url)

        val doses = mutableListOf<Dosis>()
        val jsonResponse = Json.parseToJsonElement(response).jsonArray

        for (dosisElement in jsonResponse) {
            val dosisJson = dosisElement.jsonObject
            val tomas = dosisJson["Tomas"]?.jsonArray ?: continue

            for (tomaElement in tomas) {
                val toma = tomaElement.jsonObject
                val dosis = Dosis(
                        dosisJson["Id"]?.jsonPrimitive?.long,
                        toma["Id"]?.jsonPrimitive?.long,
                        toma["FechaHora"]?.jsonPrimitive?.content,
                        dosisJson["Descripcion"]?.jsonPrimitive?.content,
                        dosisJson["Unidades"]?.jsonPrimitive?.int ?: 0
                )
                dosis.intervaloPost = dosisJson["TiempoMaximoPostergacion"]?.jsonPrimitive?.int ?: 0
                doses.add(dosis)
            }
        }

        return doses
    }
}
