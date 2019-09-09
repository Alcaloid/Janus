package com.codemobile.hackcatonapp.model

data class Deeplink(
    val info: Info,
    val item: List<Item>
)

data class Item(
    val name: String,
    val request: Request,
    val response: List<Any>
)

data class Request(
    val header: List<Any>,
    val method: String,
    val url: Url
)

data class Url(
    val host: List<String>,
    val path: List<String>,
    val port: String,
    val raw: String
)

data class Info(
    val _postman_id: String,
    val name: String,
    val schema: String
)