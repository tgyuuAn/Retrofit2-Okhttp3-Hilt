package com.example.retrofit2_okhttp3.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit2_okhttp3.R
import com.example.retrofit2_okhttp3.databinding.ActivityMainBinding
import com.example.retrofit2_okhttp3.domain.User
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    lateinit var rvAdapter: RecyclerViewAdapter
    lateinit var rv2Adapter: PagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        val userList = mutableListOf<User>()


        binding.apply {
            viewModel = mainViewModel.apply {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        callIndex.observe(this@MainActivity) {
                            tv.text = (it - 1).toString()
                        }
                    }
                }

                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        pagingData.collectLatest { data ->
                            log("MainActivity : RV2 Collect")
                            rv2Adapter.submitData(data)
                        }
                    }
                }

                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        user.collect { UserState ->
                            log("MainActivity : Collect")
                            when (UserState) {
                                is MainViewModel.UserState.Init -> {
                                    LAV.visibility = View.GONE
                                    RV.visibility = View.VISIBLE
                                }

                                is MainViewModel.UserState.Loading -> {
                                    LAV.setAnimation(R.raw.loading_gray)
                                    LAV.visibility = View.VISIBLE
                                    RV.visibility = View.GONE
                                }

                                is MainViewModel.UserState.Success -> {
                                    log("MainActivity : Collect Success")
                                    LAV.visibility = View.GONE
                                    RV.visibility = View.VISIBLE
                                    userList.add(UserState.data)
                                    rvAdapter.submitList(userList.toList())
                                    log("MainActivity : Submit 완료 " + userList.toString())
                                    mainViewModel.callIndex.value = callIndex.value?.plus(1)
                                }

                                is MainViewModel.UserState.Error -> {
                                    LAV.setAnimation(R.raw.error)
                                    LAV.visibility = View.VISIBLE
                                    LAV.playAnimation()
                                    RV.visibility = View.GONE
                                    toast("호출에 실패하셨습니다.")
                                    mainViewModel.setUserState(MainViewModel.UserState.Init, 1500L)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun log(str: String) = Log.d("tgyuu", str)
    private fun toast(str: String) = Toast.makeText(this, str, Toast.LENGTH_SHORT).show()

    private fun initRecyclerView() = binding.apply {
        val rv = binding.RV
        rvAdapter = RecyclerViewAdapter()
        rv.adapter = rvAdapter
        rv.layoutManager = LinearLayoutManager(this@MainActivity)
        rv.addItemDecoration(RecyclerViewDecoration(this@MainActivity))

        val rv2 = binding.RV2
        rv2Adapter = PagingAdapter()
        rv2.adapter = rv2Adapter
        rv.layoutManager = LinearLayoutManager(this@MainActivity)
        rv.addItemDecoration(RecyclerViewDecoration(this@MainActivity))
    }
}