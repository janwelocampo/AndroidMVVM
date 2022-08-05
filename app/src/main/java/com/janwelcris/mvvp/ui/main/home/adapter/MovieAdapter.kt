package com.janwelcris.mvvp.ui.main.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.janwelcris.mvvp.R
import com.janwelcris.mvvp.databinding.PhotoItemLayoutBinding
import com.janwelcris.mvvp.model.MovieModel

class MovieAdapter (val onPhotoSelected: (photo: MovieModel, position: Int) -> Unit) : RecyclerView.Adapter<MovieAdapter.PhotoViewHolder>() {

    private val movieItems: ArrayList<MovieModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = PhotoItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(movieItems[position], position)
    }

    override fun getItemCount() = movieItems.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(photosList: List<MovieModel>) {
        movieItems.addAll(photosList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetAdapter() {
        movieItems.clear()
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(private val itemBinding: PhotoItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(movieModel: MovieModel, position: Int) {
            itemBinding.apply {
                imgPhoto.load("https://www.themoviedb.org/t/p/w220_and_h330_face" + movieModel.poster_path) {
                    placeholder(R.color.color_box_background)
                    crossfade(true)
                }

                cardPhoto.setOnClickListener {
                    onPhotoSelected(movieModel, position)
                }
            }
        }
    }
}