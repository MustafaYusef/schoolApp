package com.smart.resources.schools_app.features.absence.getAbsence

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.sharedUi.SectionActivity
import com.smart.resources.schools_app.features.absence.addAbsence.AddAbsenceFragment

class AbsenceFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var viewModel: AbsenceViewModel

    companion object {
        fun newInstance(fm:FragmentManager){

            val fragment=
                AbsenceFragment()

            fm.beginTransaction().apply {
                add(R.id.fragmentContainer, fragment)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false)
        (activity as SectionActivity).setCustomTitle(R.string.absence)
        setHasOptionsMenu(true)

        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(AbsenceViewModel::class.java).apply {
                getAbsence().observe(this@AbsenceFragment, Observer{})
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(SharedPrefHelper.instance?.userType == UserType.TEACHER) {
            inflater.inflate(R.menu.menu_add_btn, menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addMenuItem-> fragmentManager?.let { AddAbsenceFragment.newInstance(it) }
        }
        return super.onOptionsItemSelected(item)
    }

//    private  fun onHomeworkDownload(result: Absence) {
//        var errorMsg = ""
//        when (result) {
//            is Success -> {
//                if (result.data.isNullOrEmpty()) errorMsg = getString(R.string.no_student_absence)
//                else {
//                    binding.recyclerView.adapter= AbsenceRecyclerAdapter(result.data)
//                }
//            }
//            is ResponseError -> errorMsg = result.combinedMsg
//            is ConnectionError -> errorMsg = getString(R.string.connection_error)
//        }
//
//        binding.errorText.text = errorMsg
//        binding.progressIndicator.hide()
//    }
}