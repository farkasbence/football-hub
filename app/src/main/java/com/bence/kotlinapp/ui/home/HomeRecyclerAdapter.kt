package com.bence.kotlinapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bence.kotlinapp.utils.LeagueEnum
import com.bence.kotlinapp.R
import kotlinx.android.synthetic.main.home_list_item.view.*

class HomeRecyclerAdapter(private val items : List<LeagueEnum>,
                          val context: Context,
                          listener: LeagueListClickListener) : RecyclerView.Adapter<HomeViewHolder>() {

    private val mListener: LeagueListClickListener = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.home_list_item,
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val league = items[position]

        holder.bind(league)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class HomeViewHolder (view: View, listener: LeagueListClickListener) : RecyclerView.ViewHolder(view) {

    private val mListener: LeagueListClickListener = listener
    private val leagueTextView = view.league_text_view
    private val leagueLayout = view.league_layout

    fun bind(leagueEnum: LeagueEnum) {
        leagueLayout.setOnClickListener {
            mListener.onClick(adapterPosition)
        }
        leagueTextView.text = leagueEnum.nameString
    }
}


