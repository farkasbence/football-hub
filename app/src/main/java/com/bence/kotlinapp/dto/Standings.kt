package com.bence.kotlinapp.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Standings(@Json(name = "standings") val tableList: List<Table>)