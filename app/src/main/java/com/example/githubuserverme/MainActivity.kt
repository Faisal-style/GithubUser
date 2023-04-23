package com.example.githubuserverme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserverme.adapter.MainAdapter
import com.example.githubuserverme.response.UserData
import com.example.githubuserverme.databinding.ActivityMainBinding
import com.example.githubuserverme.model.ItemsItem
import com.example.githubuserverme.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]
        viewModel.searchuser.observe(this) { searchuser -> setDataUser(searchuser) }

        viewModel.isLoading.observe(this) { showLoading(it) }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.recyclerView.addItemDecoration(itemDecoration)

    }

    private fun setDataUser(userdata: List<ItemsItem>) {
        val listUser = ArrayList<UserData>()
        for (user in userdata) {
            listUser.add(UserData(user.login, user.avatarUrl))
        }
        val adapter = MainAdapter(listUser, object : MainAdapter.OnAdapterListener {
            override fun onClick(data: UserData) {
                startActivity(
                    Intent(this@MainActivity, DetailActivity::class.java)
                        .putExtra(USERNAME, data.username)
                )
            }

        })
        binding.recyclerView.adapter = adapter
    }

    companion object {
        const val USERNAME = "username"
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_form, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {
            queryHint = "Masukkan Username"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    clearFocus()
                    searchUser(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        return true
    }

    fun searchUser(query: String) {
        viewModel.findUserGithub(query)
    }


}