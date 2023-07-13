package com.example.retrofit2_okhttp3.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.retrofit2_okhttp3.domain.GetUserUsecase
import com.example.retrofit2_okhttp3.domain.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getUserUsecase: GetUserUsecase) : ViewModel() {
    private val _callIndex = MutableLiveData(1)
    val callIndex get() = _callIndex


    private val _user = MutableStateFlow<UserState>(UserState.Init)
    val user get() = _user

    val pagingData = getUserUsecase.getUserList().cachedIn(viewModelScope)

    fun callUser() {
        viewModelScope.launch {
            log("MainViewModel : viewModelScope 진입")
            _user.emit(UserState.Loading)
            getUserUsecase(callIndex.value!!).collectLatest {
                it.fold(
                    onSuccess = { UserState.Success(it) },
                    onFailure = { UserState.Error(Throwable("Error!")) })
                    .also {
                        _user.emit(it)
                        log("MainViewModel : emit 완료 " + it.toString())
                    }
            }
        }
        log("MainViewModel : 함수 끝")
    }

    private fun log(str: String) = Log.d("tgyuu", str)

    fun setUserState(state: UserState, time : Long = 1000L) = viewModelScope.launch {
        delay(time)
        _user.emit(state)
    }

    sealed class UserState {
        object Loading : UserState()
        object Init : UserState()
        data class Success(val data: User) : UserState()
        data class Error(val error: Throwable?) : UserState()
    }
}