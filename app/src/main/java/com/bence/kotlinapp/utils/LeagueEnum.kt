package com.bence.kotlinapp.utils


enum class LeagueEnum(val nameString: String, val apiAbbreviation: String) {
    PREMIER_LEAGUE("Premier League", "PL"),
    SERIE_A("Seria A", "SA"),
    LA_LIGA("La Liga", "PD"),
    LIGUE_1("Ligue 1", "FL1"),
    BUNDES_LIGA("Bundesliga", "BL1");
}