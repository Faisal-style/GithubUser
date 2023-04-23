package com.example.githubuserverme

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserverme.adapter.MainAdapter
import com.example.githubuserverme.databinding.FragmentFollowingBinding
import com.example.githubuserverme.response.UserData
import com.example.githubuserverme.viewmodel.FollowingViewModel


class FollowingFragment : Fragment(R.layout.fragment_following), MainAdapter.OnAdapterListener {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var username: String
    private lateinit var adapter: MainAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowingBinding.bind(view)
        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowingViewModel::class.java]

        adapter = MainAdapter(ArrayList<UserData>(), this)
        binding.rvFollowing.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            this.adapter = this@FollowingFragment.adapter
        }
        searchUser(username)
        viewModel.followinguser.observe(viewLifecycleOwner) { userfollowing ->
            adapter.updateData(userfollowing.map { UserData(it.login, it.avatarUrl) })
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
            binding.progressBar4.visibility = View.VISIBLE
        } else {
            binding.progressBar4.visibility = View.INVISIBLE
        }
    }

    private fun searchUser(query: String) {
        viewModel.findUserFollowing(query)
    }

}