package com.udacity.asteroidradar.presentation.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.data.local.AsteroidDatabase
import com.udacity.asteroidradar.data.local.DatabaseHelperImpl
import com.udacity.asteroidradar.data.remote.ApiHelperImpl
import com.udacity.asteroidradar.data.remote.RetrofitClient
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.presentation.viewmodel.MainViewModel
import com.udacity.asteroidradar.repository.AsteroidRepo
import com.udacity.asteroidradar.utils.VMF

@RequiresApi(Build.VERSION_CODES.N)
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this, VMF(
                AsteroidRepo(ApiHelperImpl(RetrofitClient.getApiService()), DatabaseHelperImpl(
                    AsteroidDatabase.getInstance(requireContext())))
            )
        )[MainViewModel::class.java]
    }
    private lateinit var adapter: AsteroidAdapter
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        observeAsteroidsLoading()
        observeAsteroidsSuccess()
        observeAsteroidsError()
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.main_overflow_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.show_all_menu -> {
                        Log.d("test", "show_all_menu")
                        viewModel. getAsteroidsWeek()
                        true
                    }
                    R.id.show_rent_menu -> {
                        Log.d("test", "show_rent_menu")
                        viewModel.getAsteroidToday()
                        true
                    }
                    R.id.show_buy_menu -> {
                        Log.d("test", "show_buy_menu")
                        viewModel.getAsteroid()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    private fun observeAsteroidsLoading() {
        viewModel.asteroidsLoading.observe(viewLifecycleOwner) {
            it?.let {
                Log.d("test", "loading")
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.INVISIBLE
                }

            }
        }
    }

    private fun observeAsteroidsSuccess() {
        Log.d("test","here")
        viewModel.asteroids.observe(viewLifecycleOwner) {
            Log.d("test","asteroidsM $it")
            it?.let {
                Log.d("test", "success ${it.size}")
                adapter.submitList(it)
            }
        }
    }

    private fun observeAsteroidsError() {
        viewModel.asteroidsError.observe(viewLifecycleOwner) {
            it?.let {
                //  binding.progressBar.visibility = View.INVISIBLE
                Log.d("test", "error $it")
                if (it.isNotEmpty()) {
                    showToast(it)
                }
            }
        }
    }

    private fun setupRV() {
        adapter = AsteroidAdapter(onClickListener = AsteroidAdapter.OnClickListener {
            Log.d("test", "onClick $it")
            val action = MainFragmentDirections.actionShowDetail(it)
            findNavController().navigate(action)
        })
        binding.asteroidRecycler.adapter = adapter
    }

    private fun showToast(msg: String) {
        Log.d("test", "msg $msg")
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
