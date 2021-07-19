package com.luismiguel.retrofitvolley.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson

import com.luismiguel.retrofitvolley.databinding.ActivityDetailBinding
import com.luismiguel.retrofitvolley.model.bean.UserItem

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent

        val usuario : UserItem = Gson().fromJson(intent.getStringExtra("usuario"), UserItem::class.java)
        binding.usuario = usuario

    }
}