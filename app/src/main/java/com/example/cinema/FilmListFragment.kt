package com.example.cinema

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.data.Film
import com.example.cinema.data.FilmAdapter
import com.example.cinema.databinding.FragmentFilmListBinding
import com.example.cinema.network.ApiFactory
import com.example.cinema.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class FilmListFragment : Fragment(), FilmAdapter.Listener {

    lateinit var binding: FragmentFilmListBinding

    var Manager: LinearLayoutManager? = null
    val items = mutableListOf<Film>()
    var adapter: FilmAdapter = FilmAdapter(items, this)
    val service: ApiService = ApiFactory.getApiService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilmListBinding.inflate(inflater)
        binding.recyclerView!!.adapter = adapter
        Manager = GridLayoutManager(binding.root.context, 1)
        binding.recyclerView!!.layoutManager = Manager
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        binding.recyclerView.visibility = View.INVISIBLE
        val rotateAnimation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.duration = 1000
        rotateAnimation.repeatCount = Animation.INFINITE
        binding.loadingPicture.startAnimation(rotateAnimation)
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = service.getTopFilms()
                if (response.isSuccessful) {
                    binding.loadingPicture.clearAnimation()
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.loadingPicture.visibility = View.GONE
                    items.clear()
                    items.addAll(response.body()!!.films)
                    adapter.notifyDataSetChanged()
                } else {
                    print("else ")
                }
            } catch (e: Exception) {

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FilmListFragment()
    }

    override fun onClick(film: Film) {
        super.onClick(film)
        (requireActivity() as MainActivity).openDetails(film)
    }
}