package com.example.newapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_second.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // retrieve the intent that caused the activity to open
        val originIntent = intent
        // extract data from the intent
        val ID = originIntent.getIntExtra("ID", 0)

        game_details.text = ""

        val baseURL = "https://ghibliapi.herokuapp.com/api/"

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
        val wsCallback: Callback<Film> = object : Callback<Film> {
            override fun onResponse(
                call: Call<Film>,
                response: Response<Film>
            ) {
                if (response.code() == 200) {

                    // We got our data !
                    val responseData = response.body()
                    if (responseData != null) {
                        film_name.text = responseData.title
                        director.text = responseData.director
                        description.text = responseData.description
                        year.text = "" + responseData.release_date

                        /*Glide
                            .with(applicationContext)
                            .load(responseData.picture)
                            .into(game)*/

                        know_more_button.setOnClickListener(
                            View.OnClickListener {
                                val viewIntent: Intent = Intent(
                                    "android.intent.action.VIEW",
                                    Uri.parse(responseData.url)
                                )
                                startActivity(viewIntent)
                            }
                        )
                    }
                }
            }

            override fun onFailure(call: Call<Film>, t: Throwable) {
                // Code here what happens if calling the WebService fails
                Log.w("TAG", "WebService call failed")
            }
        }

        service.filmDetail(ID).enqueue(wsCallback)

    }
}
