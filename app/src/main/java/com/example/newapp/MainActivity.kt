package com.example.newapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*data class MovieCharacter(
    val firstName: String,
    val lastName: String,
    val movie: String,
    val goodGuy: Boolean
)*/

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // The base URL where the WebService is located
        val baseURL = "https://ghibliapi.herokuapp.com/api"

        // Use GSON library to create our JSON parser
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        // Create a Retrofit client object targeting the provided URL
        // and add a JSON converter (because we are expecting json responses)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()

        // Use the client to create a service:
        // an object implementing the interface to the WebService
        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val wsCallback: Callback<MutableList<Character>> = object: Callback<MutableList<Character>> {
            override fun onResponse(
                call: Call<MutableList<Character>>,
                response: Response<MutableList<Character>>
            ) {
                if (response.code() == 200) {
                    val responseData = response.body()
                    if (responseData != null) {
                        Log.d("TAG", "WebService success : " + responseData.size)
                        for (character in responseData)
                            Log.d("TAG", " one course : + ${character.name} ")

                        var adapter: RecyclerViewAdapter =
                            RecyclerViewAdapter(applicationContext, responseData)
                        recyclerView.setAdapter(adapter)
                        recyclerView.setLayoutManager(LinearLayoutManager(applicationContext))
                        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext,
                            DividerItemDecoration.VERTICAL))

                        val wfCallback: Callback<Film> = object : Callback<Film> {
                            override fun onResponse(
                                call: Call<Film>,
                                response: Response<Film>
                            ) {
                                if (response.code() == 200) {
                                    val responseDatas = response.body()
                                    title_film.text = responseDatas?.title
                                }
                            }
                            override fun onFailure(call: Call<Film>, t: Throwable) {
                                // Code here what happens if calling the WebService fails
                                Log.w("TAG", "WebService call failed")
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<Character>>, t: Throwable) {
                // Code here what happens if calling the WebService fails
                Log.w("TAG", "WebService call failed")
            }
        }
        service.listAllCharacters().enqueue(wsCallback)
    }
}
