package com.bence.kotlinapp.ui.fixturelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bence.kotlinapp.R
import com.bence.kotlinapp.dto.Fixture
import kotlinx.android.synthetic.main.fixture_list_item.view.*
import java.time.LocalDateTime
import java.time.ZoneId.systemDefault
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class FixtureRecyclerAdapter(private val items : ArrayList<Fixture>, val context: Context) : RecyclerView.Adapter<FixtureViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FixtureViewHolder {
        return FixtureViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.fixture_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FixtureViewHolder, position: Int) {
        val homeTeam = items[position].homeTeam.name
        val awayTeam = items[position].awayTeam.name
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        val localDate = convertToLocalDateViaInstant(items[position].utcDate)

        holder.bind(homeTeam, awayTeam, formatter.format(localDate))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun convertToLocalDateViaInstant(dateToConvert: Date): LocalDateTime {
        return dateToConvert.toInstant()
            .atZone(systemDefault())
            .toLocalDateTime()
    }
}

class FixtureViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val fixtureHomeTeamTextView = view.fixture_home_team_text_view
    private val fixtureAwayTeamTextView = view.fixture_away_team_text_view
    private val matchTimeView = view.match_time_text_view

    fun bind(homeTeamString: String, awayTeamString: String, timeString: String) {
        fixtureHomeTeamTextView.text = homeTeamString
        fixtureAwayTeamTextView.text = awayTeamString
        matchTimeView.text = timeString
    }
}


