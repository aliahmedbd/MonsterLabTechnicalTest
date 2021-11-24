package com.monsterlab.technicaltest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monsterlab.technicaltest.R
import com.monsterlab.technicaltest.databinding.FragmentImageListBinding
import com.monsterlab.technicaltest.databinding.FragmentSettingsBinding
import com.monsterlab.technicaltest.utils.Constants
import java.lang.Exception

/**
 * Created by Ali Ahmed, mail: aliahmedaiub@gmail.com
 */
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.edtImageCount.setText(Constants.IMAGE_PAGE_COUNT.toString())
        binding.edtPageLimit.setText(Constants.TOTAL_PAGE_COUNT.toString())
        clickListener()
        return root
    }

    private fun clickListener() {
        binding.apply {
            btnApply.setOnClickListener {
                if (validForm())
                    Constants.IMAGE_PAGE_COUNT = edtImageCount.text.toString().toInt()
                Constants.TOTAL_PAGE_COUNT = edtPageLimit.text.toString().toInt()
            }
        }
    }

    private fun validForm(): Boolean {
        try {

            binding.apply {
                if (edtImageCount.text.toString().isEmpty() ||
                    edtImageCount.text.toString().toInt() == 0
                ) {
                    edtImageCount.error = "Please input valid data"
                    return false
                } else if (edtPageLimit.text.toString().isEmpty() ||
                    edtPageLimit.text.toString().toInt() == 0
                ) {
                    edtPageLimit.error = "Please input valid data"
                    return false
                }
                return true
            }
        } catch (e: Exception) {
            return false
        }
    }
}