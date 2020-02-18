package com.bence.kotlinapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bence.kotlinapp.utils.LeagueEnum
import com.bence.kotlinapp.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val leagues = listOf(
        LeagueEnum.PREMIER_LEAGUE,
        LeagueEnum.BUNDES_LIGA,
        LeagueEnum.LA_LIGA,
        LeagueEnum.LIGUE_1,
        LeagueEnum.SERIE_A)

    private lateinit var listener: OnLeagueSelected

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnLeagueSelected) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement OnLeagueSelected.")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.home_header)

        val listener = object : LeagueListClickListener {
            override fun onClick(position: Int) {
                listener.onLeagueItemSelected(leagues[position])
            }
        }
        leagues_recycler_view.layoutManager = GridLayoutManager(context, 2)
        leagues_recycler_view.adapter = context?.let {
            HomeRecyclerAdapter(leagues, it, listener)
        }
    }

    interface OnLeagueSelected {
        fun onLeagueItemSelected(selectedLeague: LeagueEnum)
    }
}