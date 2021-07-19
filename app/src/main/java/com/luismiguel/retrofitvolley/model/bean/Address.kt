package com.luismiguel.retrofitvolley.model.bean

data class  Address(
    val city: String,
    val geo: Geo,
    val street: String,
    val suite: String,
    val zipcode: String
)