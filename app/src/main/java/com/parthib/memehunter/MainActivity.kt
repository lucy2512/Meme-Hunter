package com.parthib.memehunter

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }

    private fun loadmeme(){

        var memeimage: ImageView =findViewById(R.id.memeImage)
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = " https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
        Response.Listener { response ->
            val url = response.getString("url")
            Glide.with(this).load(url).into(memeimage)
        },
        Response.ErrorListener { error ->
            // TODO: Handle error
        }
        )

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    fun shareMeme(view: View) {}
    fun nextMeme(view: View) {
        loadmeme()
    }
}