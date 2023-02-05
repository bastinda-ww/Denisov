package com.example.cinema

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.cinema.data.Film
import com.example.cinema.databinding.FragmentDetailsFilmBinding
import com.example.cinema.network.ApiFactory
import com.example.cinema.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailsFilmFragment : Fragment() {

    lateinit var binding: FragmentDetailsFilmBinding
    private var film: Film? = null
    private val service: ApiService = ApiFactory.getApiService()
    private val token: String = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        (film)?.let { film ->
            binding.filmName.text = film.nameRu
            binding.filmCountry.text = film.countries.get(0).country
            binding.filmGenre.text = film.genres.get(0).genre
            Glide.with(binding.root.context)
                .load(film.posterURL)
                .skipMemoryCache(true)//for caching the image url in case phone is offline
                .into(binding.poster)
        }
        binding.poster.visibility = View.INVISIBLE
        binding.filmCountry.visibility = View.INVISIBLE
        binding.filmName.visibility = View.INVISIBLE
        binding.filmDescription.visibility = View.INVISIBLE
        binding.filmGenre.visibility = View.INVISIBLE

        val rotateAnimation = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotateAnimation.duration = 1000
        rotateAnimation.repeatCount = Animation.INFINITE
        binding.loadingPicture.startAnimation(rotateAnimation)
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = service.getFilmById(token, film!!.filmID)
                if (response.isSuccessful) {
//                    binding.loadingPicture.clearAnimation()
//                    binding.loadingPicture.visibility = View.GONE
                    binding.poster.visibility = View.VISIBLE
                    binding.filmCountry.visibility = View.VISIBLE
                    binding.filmName.visibility = View.VISIBLE
                    binding.filmDescription.visibility = View.VISIBLE
                    binding.filmGenre.visibility = View.VISIBLE
                    binding.filmDescription.text = response.body()!!.description
                } else {
                    print("else")
                    // Handle the error
                }
            } catch (e: Exception) {
                // Handle the exception
                print("catch")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsFilmBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = DetailsFilmFragment()
    }

    fun setFilmData(film: Film) {
        this.film = film
    }
}