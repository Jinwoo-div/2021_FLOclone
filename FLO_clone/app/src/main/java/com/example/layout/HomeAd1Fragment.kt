package com.example.layout

import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.layout.databinding.FragmentHomeAd1Binding

class HomeAd1Fragment(val img: Int):Fragment() {
    lateinit var binding: FragmentHomeAd1Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeAd1Binding.inflate(inflater, container, false)

        binding.homeAd1Btn.setImageResource(img)

        return binding.root
    }
}