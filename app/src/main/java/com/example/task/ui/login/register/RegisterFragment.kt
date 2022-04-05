package com.example.task.ui.login.register

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
import com.example.task.databinding.FragmentRegisterBinding
import com.example.task.model.User
import com.example.task.ui.home.HomeActivity
import com.example.task.ui.login.EXTRAS_USERNAME
import com.example.task.ui.login.UserViewModel
import com.example.task.ui.login.UserViewModelFactory
import com.google.firebase.analytics.FirebaseAnalytics

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var binding: FragmentRegisterBinding
    private var viewModel : UserViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())

        binding.registerRegisterBtn.setOnClickListener {
            val name = binding.registerNameEt.text.toString()
            val username = binding.registerUsernameEt.text.toString()
            val password = binding.registerPasswordEt.text.toString()
            val user = User(username, password, name)

            if (viewModel!!.registerCheck(username)) {

                val bundle = Bundle().apply {
                    putString("full_name", name)
                    putString("username", username)
                    putString("password", password)
                }
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle)

                viewModel!!.insertUser(user)
                val intent = Intent(requireContext(), HomeActivity::class.java)
                intent.putExtra(EXTRAS_USERNAME, username)
                startActivity(intent)

            } else {
                Toast.makeText(
                    requireContext(),
                    "This username is already taken.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.registerLoginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val factory = UserViewModelFactory()
        viewModel = ViewModelProvider(requireActivity(),factory).get(UserViewModel::class.java)
    }
}