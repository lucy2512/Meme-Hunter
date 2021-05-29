package com.parthib.memehunter

import android.app.DownloadManager
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loadmeme()
    }

    var currentImageUrl : String ?= null

    private fun loadmeme(){

        var memeimage: ImageView =findViewById(R.id.memeImage)
        var progressbar: ProgressBar = findViewById(R.id.progressbar)
        progressbar.visibility=View.VISIBLE
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = " https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
        Response.Listener { response ->
            currentImageUrl = response.getString("url")
            Glide.with(this).load(currentImageUrl).listener(object: RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressbar.visibility=View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressbar.visibility=View.GONE
                    return false
                }
            }).into(memeimage)
        },
        Response.ErrorListener { error ->
            Toast.makeText(this,"Something went wrong", Toast.LENGTH_LONG).show()
        }
        )

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    fun shareMeme(view: View) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, Checkout this cool meme $currentImageUrl")
        val chooser = Intent.createChooser(intent, "Share this meme using...")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        loadmeme()
    }
}