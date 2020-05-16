package com.bence.kotlinapp.dto

import java.util.*

data class Fixture(val homeTeam: Team,
                   val awayTeam: Team,
                   val utcDate: Date)