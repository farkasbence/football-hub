package com.bence.kotlinapp.ui.leaguetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bence.kotlinapp.R
import com.bence.kotlinapp.dto.RankedTeam
import com.bence.kotlinapp.dto.Standings
import com.bence.kotlinapp.utils.LeagueEnum
import kotlinx.android.synthetic.main.fragment_league_table.*
import org.json.JSONException

class LeagueTableFragment : Fragment(), LeagueTablePresenter.View {

    private var teams: ArrayList<RankedTeam> = ArrayList()
    private lateinit var selectedLeague: LeagueEnum
    private lateinit var presenter: LeagueTablePresenter

    companion object {
        private const val ARG_SELECTED_LEAGUE = "ARG_SELECTED_LEAGUE"

        @JvmStatic
        fun newInstance(selectedLeague: LeagueEnum) =
            LeagueTableFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_SELECTED_LEAGUE, selectedLeague)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = LeagueTablePresenter()
        arguments?.let {
            selectedLeague = (it.getSerializable(ARG_SELECTED_LEAGUE) as LeagueEnum?)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_league_table, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title = selectedLeague.nameString

        fetchLeagueTable(selectedLeague)
    }

    override fun showLeagueTable(standings: Standings) {
        activity?.runOnUiThread{
            try {
                this@LeagueTableFragment.fetchComplete(standings)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    override fun showError() {
        activity?.runOnUiThread{
            try {
                Toast.makeText(context, "Failed to fetch league data", Toast.LENGTH_LONG).show()

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchComplete(standings: Standings?) {
        teams.clear()
        if (standings != null) {
            val table = standings.standings[0]
            val teamList = table.table
            for (team in teamList) {
                teams.add(team)
            }
        }

        league_recycler_view.layoutManager = LinearLayoutManager(context)
        league_recycler_view.adapter = context?.let {
            LeagueRecyclerAdapter(
                teams,
                it
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    private fun fetchLeagueTable(selectedLeague: LeagueEnum) {
        presenter.fetchLeagueTable(selectedLeague)
    }
}
