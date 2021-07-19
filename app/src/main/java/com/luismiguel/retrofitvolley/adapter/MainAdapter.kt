package com.luismiguel.retrofitvolley.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.luismiguel.retrofitvolley.model.bean.UserItem
import com.luismiguel.retrofitvolley.R
import com.luismiguel.retrofitvolley.activity.DetailActivity
import java.util.ArrayList

class MainAdapter(private val listener: OnItemClickListener): RecyclerView.Adapter<MainAdapter.PersonasHolder>() {
    private var listaActividades: List<UserItem> = ArrayList(0)

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    override fun getItemCount(): Int {
        return listaActividades.size ?: 0
    }

    fun bindData(usuarios: List<UserItem>) {
        listaActividades = ArrayList(usuarios)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonasHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuario, parent, false)

        return PersonasHolder(itemView)
    }

    override fun onBindViewHolder(holder: PersonasHolder, position: Int) {
        holder.nombre.text = listaActividades[position].name
        holder.nombre.setOnClickListener{
            val intent = Intent(holder.nombre.context, DetailActivity::class.java)
            val usuarioJson = Gson().toJson(listaActividades[position])
            intent.putExtra("usuario", usuarioJson)
            holder.nombre.context.startActivity(intent)
        }

    }

    inner class PersonasHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val nombre: Button = itemView.findViewById(R.id.nombre)

        //var itemViews: View ? = null
        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.i("onclick", "adapterView")
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }

        }


    }

}