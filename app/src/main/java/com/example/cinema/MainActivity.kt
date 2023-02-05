package com.example.cinema

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cinema.data.Film
import com.example.cinema.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val filmListFragment = FilmListFragment.newInstance()
    private val detailsFilmFragment = DetailsFilmFragment.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFragment(filmListFragment, R.id.main_activity_holder)
    }

    private fun openFragment(fragment: Fragment, idHolder: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(idHolder, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentById(R.id.main_activity_holder) is DetailsFilmFragment) {
            openFragment(filmListFragment, R.id.main_activity_holder)
        } else {
            super.onBackPressed()
        }
    }

    fun openDetails(film: Film) {
        detailsFilmFragment.setFilmData(film)
        openFragment(detailsFilmFragment, R.id.main_activity_holder)

    }
}