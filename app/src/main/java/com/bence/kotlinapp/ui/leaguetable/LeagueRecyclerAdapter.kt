package com.bence.kotlinapp.ui.leaguetable

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bence.kotlinapp.R
import com.bence.kotlinapp.dto.RankedTeam
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.league_list_item.view.*
import java.util.*


class LeagueRecyclerAdapter(private val items : ArrayList<RankedTeam>, val context: Context)
    : RecyclerView.Adapter<LeagueViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        return LeagueViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.league_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        val leaguePosition = items[position].position
        val team = items[position].team.name
        val points = items[position].points
        val crestUrl = items[position].team.crestUrl

        holder.bind(leaguePosition, team, points, crestUrl)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class LeagueViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    private val positionTextView = view.league_position_text_view
    private val teamTextView = view.league_team_text_view
    private val pointsTextView = view.league_points_text_view
    private val crestImageView = view.crest_image_view

    fun bind(positionString: String, teamString: String, pointsString: String, crestUrl: String) {
        positionTextView.text = positionString
        teamTextView.text = teamString
        pointsTextView.text = pointsString

        val picasso = Picasso.get()
        picasso
            .load(crestUrl)
            .placeholder(R.drawable.placeholder)
            .into(crestImageView)
    }
}


