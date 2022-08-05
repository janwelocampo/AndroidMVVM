package com.janwelcris.mvvp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.janwelcris.mvvp.base.navigation.NavigationCommand

abstract class BaseFragment<BINDING : ViewBinding?, VM : BaseFragmentViewModel> : Fragment() {
    protected abstract val viewModel: VM

    private var _binding: BINDING? = null
    protected val binding: BINDING? get() = _binding

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BINDING

    protected abstract fun onReady(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigation()

        onReady(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeNavigation() {
        viewModel.navigation.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { navigationCommand ->
                when (navigationCommand) {
                    is NavigationCommand.ToDirection -> findNavController().navigate(navigationCommand.resId, navigationCommand.bundle)
                    is NavigationCommand.Back -> findNavController().navigateUp()
                }
            }
        }
    }


}