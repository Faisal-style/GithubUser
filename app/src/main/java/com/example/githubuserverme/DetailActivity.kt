package com.example.githubuserverme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.githubuserverme.MainActivity.Companion.USERNAME
import com.example.githubuserverme.adapter.SectionPagerAdapter
import com.example.githubuserverme.databinding.ActivityDetailBinding
import com.example.githubuserverme.model.ResponseDetaiUser
import com.example.githubuserverme.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.content_follower,
            R.string.following_content
        )
        const val EXTRA_USERNAME = "extra_username"
    }

    private fun sectionPager(extraUsername : Bundle){
        val sectionPagerAdapter = SectionPagerAdapter(this, extraUsername)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter
        val tabs : TabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabs, viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        viewModel.detailUser.observe(this){detailuser -> setDetailUser(detailuser)}
        viewModel.isLoading.observe(this){showLoading(it)}


        val nama = intent.getStringExtra(USERNAME).toString()
        val bundle =Bundle().apply {
            putString(EXTRA_USERNAME, nama)
        }
        sectionPager(bundle)
        detailUser(nama)

    }


    fun setDetailUser(userDetail: ResponseDetaiUser){
        binding.tvName.text = userDetail.login
        binding.tvUserLocation.text = userDetail.location
        Glide.with(this)
            .load(userDetail.avatarUrl)
            .transform(CircleCrop())
            .into(binding.userAvatarImg)
    }

    private fun detailUser(query: String){
        viewModel.userDetail(query)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.INVISIBLE
        }
    }

}