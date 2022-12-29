package com.imaginato.homeworkmvvm.ui.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.imaginato.homeworkmvvm.data.remote.login.LoginRepository
import com.imaginato.homeworkmvvm.ui.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.inject


@KoinApiExtension
class LoginActivityViewModel: BaseViewModel() {
    private val repository: LoginRepository by inject()

    private var _progress: MutableLiveData<Boolean> = MutableLiveData()

    private var _resultLiveData1: MutableLiveData<String> = MutableLiveData()

    val progress: LiveData<Boolean>
        get() {
            return _progress
        }

    val resultLiveData1: MutableLiveData<String>
        get() {
            return _resultLiveData1
        }


    /**
     * Do something and handle business logic here
     * */
    fun getLoginData(s: String, s1: String) {
        viewModelScope.launch {

            val paramObject = JSONObject()
            paramObject.put("username", s)
            paramObject.put("password", s1)

            repository.getLoginData(s,s1,paramObject)
                .onStart {
                    _progress.value = true
                }.catch {
                    _progress.value = false
                }
                .onCompletion {
                }.collect {
                    _progress.value = false

                    _resultLiveData1.value= it.toString()

                }
        }
    }
}
