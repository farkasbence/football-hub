package com.bence.kotlinapp.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Table(@Json(name = "table") val rankedTeamList: List<RankedTeam>)