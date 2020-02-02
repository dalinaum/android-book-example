package com.example.viewModelImage

data class Response(
        var count: Int,
        var previous: String,
        var next: String,
        var results: List<Result>
)
