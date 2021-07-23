package com.luismiguel.retrofitvolley.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response as Responseolley
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.luismiguel.retrofitvolley.api.IRetrofit
import com.luismiguel.retrofitvolley.BuildConfig
import com.luismiguel.retrofitvolley.MySingleton
import com.luismiguel.retrofitvolley.OkHttp3Stack
import com.luismiguel.retrofitvolley.R
import com.luismiguel.retrofitvolley.adapter.MainAdapter
import com.luismiguel.retrofitvolley.model.bean.UserItem
import com.luismiguel.retrofitvolley.model.bean.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), MainAdapter.OnItemClickListener {
    private lateinit var retrofit: Retrofit
    private lateinit var buttonRetrofit: Button
    private lateinit var buttonVolley: Button
    private lateinit var mainAdapter: MainAdapter
    private lateinit var rvUsuarios: RecyclerView
    private lateinit var listener: MainActivity
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private lateinit var usuariosApi: IRetrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        retrofitInit()
        buttonRetrofit = findViewById(R.id.btnTraerListaRetrofit)
        buttonVolley = findViewById(R.id.btnTraerListaVolley)
        usuariosApi = retrofit.create(IRetrofit::class.java)
        rvUsuarios = findViewById(R.id.rvUsuarios)
        listener = this
        mainAdapter = MainAdapter(this)
        buttonVolley.setOnClickListener{
            Log.i("volley", "traer lista con volley")
            val url = "https://jsonplaceholder.typicode.com/users"
            rvUsuarios.visibility = View.GONE
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                Responseolley.Listener { response ->
                    //Log.i("volley", response.toString())
                    val lista : List<UserItem> = Gson().fromJson(response.toString(), Users::class.java)
                    mainAdapter.bindData(lista)
                    rvUsuarios.layoutManager = LinearLayoutManager(applicationContext)
                    rvUsuarios.hasFixedSize()
                    rvUsuarios.adapter = mainAdapter
                    rvUsuarios.visibility = View.VISIBLE
                },
                Responseolley.ErrorListener { error ->
                    Log.i("volley", "error ${error.toString()}")
                    error.stackTrace
                }
            )

            val queue = Volley.newRequestQueue(this, OkHttp3Stack())
            queue.add(stringRequest)
            MySingleton.getInstance(this).addToRequestQueue(stringRequest)
        }

        buttonRetrofit.setOnClickListener {


            rvUsuarios.visibility = View.GONE

            fetchUsers()
            rvUsuarios.layoutManager = LinearLayoutManager(applicationContext)
            rvUsuarios.hasFixedSize()
            rvUsuarios.adapter = mainAdapter
            rvUsuarios.visibility = View.VISIBLE
        }
    }

    fun retrofitInit(){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okBuilder = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        retrofit = Retrofit.Builder().baseUrl(BuildConfig.UrlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okBuilder)
            .build()

    }

    fun fetchUsers(){
        coroutineScope.launch {
            val response = usuariosApi.consultarusuarios()
            //usuarios?.value = response?.body()
            mainAdapter.bindData(response?.body()!!)

        }
    }

    override fun onItemClick(position: Int) {
        Log.i("onItemClick", ""+ position)
    }
}