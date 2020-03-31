package com.bence.kotlinapp.ui.fixturelist

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bence.kotlinapp.R
import com.bence.kotlinapp.dto.Fixture
import com.bence.kotlinapp.dto.FixtureList
import com.bence.kotlinapp.utils.LeagueEnum
import kotlinx.android.synthetic.main.fragment_fixture_list.*
import org.json.JSONException
import java.util.*

class FixtureListFragment : Fragment(), FixtureListPresenter.View {

    private var fixtures: ArrayList<Fixture> = ArrayList()
    private var selectedMatchDay = "1"
    private lateinit var presenter: FixtureListPresenter
    private lateinit var listener: OnLeagueSelected
    private lateinit var selectedLeague: LeagueEnum

    companion object {
        private const val ARG_SELECTED_LEAGUE = "ARG_SELECTED_LEAGUE"

        @JvmStatic
        fun newInstance(selectedLeague: LeagueEnum) =
            FixtureListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_SELECTED_LEAGUE, selectedLeague)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = FixtureListPresenter()
        arguments?.let {
            selectedLeague = (it.getSerializable(ARG_SELECTED_LEAGUE) as LeagueEnum?)!!
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fixture_list, container, false)
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
        presenter.attach(this)

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title = getString(R.string.fixtures_header)

        if (fixtures.isEmpty()) {
            fetchFixtureList(selectedLeague, selectedMatchDay)
        } else {
            initRecyclerView()
        }

        league_title.text = selectedLeague.nameString
        league_title.setOnClickListener { listener.onLeagueSelected(selectedLeague)}
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.calendar_menu, menu)

        val menuItem = menu?.findItem(R.id.action_calendar)
        val subMenu = menuItem?.subMenu
        val matchDays = resources.getStringArray(R.array.match_day_array)
        for (matchDay: String in matchDays) {
            subMenu?.add(1, 0, 0, matchDay)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val matchDay = item?.title
        val groupId = item?.groupId
        if (groupId == 1) {
            if (matchDay != selectedMatchDay) {
                fetchFixtureList(selectedLeague, matchDay as String)
            }
            selectedMatchDay = matchDay as String
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showFixtureList(fixtureList: FixtureList) {
        activity?.runOnUiThread {
            try {
                this@FixtureListFragment.fetchComplete(fixtureList)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    override fun showError() {
        activity?.runOnUiThread{
            try {
                Toast.makeText(context, "Failed to fetch fixture data", Toast.LENGTH_LONG).show()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchComplete(fixtureList: FixtureList) {
        fixtures.clear()

        for (fixture in fixtureList.matches) {
            fixtures.add(fixture)
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        fixtures_recycler_view.layoutManager = LinearLayoutManager(context)
        fixtures_recycler_view.adapter = context?.let {
            FixtureRecyclerAdapter(fixtures, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    private fun fetchFixtureList(selectedLeague: LeagueEnum, selectedMatchDay: String) {
        presenter.fetchFixtureList(selectedLeague, selectedMatchDay)
    }

    interface OnLeagueSelected {
        fun onLeagueSelected(selectedLeague: LeagueEnum)
    }
}
