package com.bence.kotlinapp.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class Fixture(@Json(name = "homeTeam") val homeTeam: Team,
                   @Json(name = "awayTeam") val awayTeam: Team,
                   @Json(name = "utcDate") val time: Date
)