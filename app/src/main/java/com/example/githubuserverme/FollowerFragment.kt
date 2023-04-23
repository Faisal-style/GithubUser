package com.example.githubuserverme

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserverme.adapter.MainAdapter
import com.example.githubuserverme.databinding.FragmentFollowerBinding
import com.example.githubuserverme.response.UserData
import com.example.githubuserverme.viewmodel.FollowerViewModel


class FollowerFragment : Fragment(R.layout.fragment_follower), MainAdapter.OnAdapterListener {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowerViewModel
    private lateinit var username: String
    private lateinit var adapter: MainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowerBinding.bind(view)
        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowerViewModel::class.java]

        adapter = MainAdapter(ArrayList<UserData>(), this)
        binding.rvFollower.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            this.adapter = this@FollowerFragment.adapter
        }
        searchUser(username)
        viewModel.followeruser.observe(viewLifecycleOwner) { userfollower ->
            adapter.updateData(userfollower.map { UserData(it.login, it.avatarUrl) })
        }
        viewModel.isLoading.observe(viewLifecycleOwner){showLoading(it)}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(data: UserData) {
        startActivity(
            Intent(requireContext(), DetailActivity::class.java)
            .putExtra(DetailActivity.EXTRA_USERNAME, data.username)
        )
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar3.visibility = View.VISIBLE
        } else {
            binding.progressBar3.visibility = View.INVISIBLE
        }
    }

    private fun searchUser(query: String) {
        viewModel.findUserFollower(query)
    }
}

