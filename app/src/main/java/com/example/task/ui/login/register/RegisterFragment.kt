package com.example.task.ui.login.register

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
import com.example.task.model.User
import com.example.task.ui.home.HomeActivity
import com.example.task.ui.login.UserViewModel
import com.example.task.ui.login.UserViewModelFactory


class RegisterFragment : Fragment(R.layout.fragment_register) {
    val factory = UserViewModelFactory()
    var model :UserViewModel?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val registerButton = view.findViewById<Button>(R.id.register_register_btn)
        val loginButton = view.findViewById<Button>(R.id.register_login_btn)

        registerButton.setOnClickListener {
            val name = view.findViewById<EditText>(R.id.register_name_et).text.toString()
            val username = view.findViewById<EditText>(R.id.register_username_et).text.toString()
            val password = view.findViewById<EditText>(R.id.register_password_et).text.toString()
            val user = User(0,username, password,name)
            if (model!!.registerCheck(username)){
                model!!.insertUser(user)
                val intent = Intent(requireContext(),HomeActivity::class.java)
                startActivity(intent)
            }
            else
                Toast.makeText(requireContext(), "This username is already taken.", Toast.LENGTH_SHORT).show()
        }
        loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        model = ViewModelProvider(requireActivity(),factory).get(UserViewModel::class.java)
    }
}