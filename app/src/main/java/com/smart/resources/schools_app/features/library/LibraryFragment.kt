package com.smart.resources.schools_app.features.library

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.myTypes.ConnectionError
import com.smart.resources.schools_app.core.myTypes.ResponseError
import com.smart.resources.schools_app.core.myTypes.Success
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.sharedUi.SectionActivity
import com.smart.resources.schools_app.core.utils.*

class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var viewModel: LibraryViewModel

    companion object {
        fun newInstance(fm:FragmentManager){

            val fragment=
                LibraryFragment()

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
            .apply {
            lifecycleOwner= this@LibraryFragment
        }
        (activity as SectionActivity).setCustomTitle(R.string.library)

        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(LibraryViewModel::class.java).apply {
                binding.listState= listState
                getBooks().observe(this@LibraryFragment, Observer{onHomeworkDownload(it)})
            }
    }

    private  fun onHomeworkDownload(result: List<LibraryModel>) {
        binding.recyclerView.createGridLayout(LibraryRecyclerAdapter(result))

    }
}
