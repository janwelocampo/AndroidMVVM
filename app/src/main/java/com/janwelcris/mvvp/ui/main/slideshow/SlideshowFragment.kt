package com.janwelcris.mvvp.ui.main.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.janwelcris.mvvp.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this)[SlideshowViewModel::class.java]

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        slideshowViewModel.text.observe(viewLifecycleOwner) {
            binding.textSlideshow.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}