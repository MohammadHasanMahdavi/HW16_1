package com.example.task.ui.login.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.ui.home.HomeActivity
import com.example.task.ui.login.UserViewModel
import com.example.task.ui.login.UserViewModelFactory


class LoginFragment : Fragment(R.layout.fragment_login) {
    val factory = UserViewModelFactory()
    var model : UserViewModel?= null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginButton = view.findViewById<Button>(R.id.login_btn)
        val registerButton = view.findViewById<Button>(R.id.register_btn)

        loginButton.setOnClickListener {
            val username = view.findViewById<EditText>(R.id.username_et).text.toString()
            val password = view.findViewById<EditText>(R.id.password_et).text.toString()
            if (model!!.loginCheck(username,password))
            {
                val intent = Intent(requireContext(),HomeActivity::class.java)
                startActivity(intent)
            }
            else
                Toast.makeText(requireContext(), "username or password is wrong.", Toast.LENGTH_SHORT).show()
        }
        registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model = ViewModelProvider(requireActivity(),factory).get(UserViewModel::class.java)
    }
}