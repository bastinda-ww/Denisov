package com.example.cinema.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.cinema.R
import com.example.cinema.databinding.CardOfFilmBinding

class FilmAdapter(private val filmList: List<Film>, private val listener: Listener): RecyclerView.Adapter<FilmAdapter.FilmHolder>() {

    class FilmHolder(item: View): RecyclerView.ViewHolder(item) {

        val binding = CardOfFilmBinding.bind(item)
        fun bind (film: Film, listener: Listener) = with(binding){
            filmName.text = film.nameRu
            val genre = "${film.genres.get(0).genre}, (${film.year})"
            filmGenre.text = genre
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(FitCenter(), RoundedCorners(16))
            Glide.with(binding.root.context)
                .load(film.posterURLPreview)
                .apply(requestOptions)
                .skipMemoryCache(true)
                .into(smallPoster)
            itemView.setOnClickListener{
                listener.onClick(film)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_of_film, parent, false)
        return FilmHolder(view)
    }

    override fun onBindViewHolder(holder: FilmHolder, position: Int) {
        holder.bind(filmList[position], listener)
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    interface Listener{
        fun onClick(film: Film){

        }
    }
}
