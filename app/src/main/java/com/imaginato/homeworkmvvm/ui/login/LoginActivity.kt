package com.imaginato.homeworkmvvm.ui.login


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.imaginato.homeworkmvvm.databinding.LoginActivityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModel<LoginActivityViewModel>()
    private lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btLogin.setOnClickListener {

            val username: String = binding.etUsername.text.toString().trim()
            val password: String = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty())
            {
                Toast.makeText(this,"Username or password should not be empty",Toast.LENGTH_SHORT).show()
            }else {
                viewModel.getLoginData(username, password)
            }

        }
        initObserve()
    }

    private fun initObserve() {
        viewModel.resultLiveData1.observe(this, Observer {

            binding.etUsername.text.clear()
            binding.etPassword.text.clear()

        })

        viewModel.progress.observe(this, Observer {
            binding.pbLoading.isVisible = it
        })

    }

}
