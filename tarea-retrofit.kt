import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.Call

// se definer la interfaz de Retrofit con las solicitudes que se necesitan
interface ApiService {
    @GET("/users")  // URL para obtener la lista de usuarios
    fun obtenerUsuarios(): Call<List<Usuario>>  // Retorna una lista de objetos 'Usuario'
}

data class Usuario(val id: Int, val nombre: String, val usuario: String, val correo: String)

// Función principal donde se configura Retrofit y se hace la solicitud
fun main() {
    // Configuramos Retrofit con la URL base de JSON Placeholder
    val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")  // url de la api
        .addConverterFactory(GsonConverterFactory.create())  // se convierte en JSON
        .build()

    // se crea lainstancia de la interfaz
    val apiService = retrofit.create(ApiService::class.java)

    // se hace el llamado a la API para obtener la lista de usuarios
    val llamada = apiService.obtenerUsuarios()

    // se envia  la solicitud de manera asíncrona
    llamada.enqueue(object : retrofit2.Callback<List<Usuario>> {
        // Si la solicitud es exitosa
        override fun onResponse(call: Call<List<Usuario>>, response: retrofit2.Response<List<Usuario>>) {
            if (response.isSuccessful) {
                val usuarios = response.body()  //se obtiene la resuesta
                usuarios?.forEach { usuario ->
                    
                    println("ID: ${usuario.id}, Nombre: ${usuario.nombre}, Usuario: ${usuario.usuario}, Correo: ${usuario.correo}")
                }
            }
        }

        
        override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
            // imprimir un mensaje de que fallo
            println("Error: ${t.message}")
        }
    })
}
