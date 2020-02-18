package com.bence.kotlinapp.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FixtureList(@Json(name = "matches") val fixtures: List<Fixture>)