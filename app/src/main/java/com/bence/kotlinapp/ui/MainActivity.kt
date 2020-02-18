package com.bence.kotlinapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bence.kotlinapp.R
import com.bence.kotlinapp.utils.LeagueEnum
import com.bence.kotlinapp.ui.fixturelist.FixtureListFragment
import com.bence.kotlinapp.ui.home.HomeFragment
import com.bence.kotlinapp.ui.leaguetable.LeagueTableFragment

class MainActivity : AppCompatActivity(), FixtureListFragment.OnLeagueSelected, HomeFragment.OnLeagueSelected {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_layout, HomeFragment.newInstance())
                .commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onLeagueSelected(selectedLeague: LeagueEnum) {
        val leagueFragment = LeagueTableFragment.newInstance(selectedLeague)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.root_layout, leagueFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onLeagueItemSelected(selectedLeague: LeagueEnum) {
        val fixtureListFragment = FixtureListFragment.newInstance(selectedLeague)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.root_layout, fixtureListFragment)
            .addToBackStack(null)
            .commit()
    }
}
