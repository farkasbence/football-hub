package com.bence.kotlinapp.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RankedTeam(@Json(name = "team") val team: Team,
                      @Json(name = "position") val position: String,
                      @Json(name = "points") val points: String)