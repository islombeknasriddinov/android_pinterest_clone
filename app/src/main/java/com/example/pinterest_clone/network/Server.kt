package com.example.pinterest_clone.network

object Server {
    val IS_TESTER = true
    init {
        System.loadLibrary("keys")
    }

    external fun getDevelopment(): String
    external fun getProduction(): String
    external fun getAccessKey(): String
    external fun getClientId(): String
}