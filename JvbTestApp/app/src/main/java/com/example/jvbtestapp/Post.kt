package com.example.jvbtestapp

import com.fasterxml.jackson.annotation.JsonProperty

data class Post (
    @JsonProperty("id") val id: Long,
    @JsonProperty("userId") val userId: Long,
    @JsonProperty("title") val title: String,
    @JsonProperty("body") val body: String
)