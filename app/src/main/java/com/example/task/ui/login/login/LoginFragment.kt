package com.example.task.ui.login.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.databinding.FragmentLoginBinding
import com.example.task.ui.home.HomeActivity
import com.example.task.ui.login.EXTRAS_USERNAME
import com.example.task.ui.login.UserViewModel
import com.example.task.ui.login.UserViewModelFactory
import com.google.firebase.analytics.FirebaseAnalytics


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private var viewModel: UserViewModel? = null
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())

        binding.loginBtn.setOnClickListener {
            val username = binding.usernameEt.text.toString()
            val password = binding.passwordEt.text.toString()

            if (viewModel!!.loginCheck(username, password)) {

                val bundle = Bundle().apply {
                    putString("username", username)
                    putString("password", password)
                }
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)

                val intent = Intent(requireContext(), HomeActivity::class.java)
                intent.putExtra(EXTRAS_USERNAME, username)
                startActivity(intent)

            } else {
                Toast.makeText(
                    requireContext(),
                    "username or password is wrong.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val factory = UserViewModelFactory()
        viewModel = ViewModelProvider(requireActivity(),factory).get(UserViewModel::class.java)
    }
}
