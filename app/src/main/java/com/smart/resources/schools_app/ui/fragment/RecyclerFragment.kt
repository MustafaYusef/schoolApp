package com.smart.resources.schools_app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.adapter.sections.*
import com.smart.resources.schools_app.database.dao.ExamDao
import com.smart.resources.schools_app.database.dao.HomeworkDao
import com.smart.resources.schools_app.database.dao.LibraryDao
import com.smart.resources.schools_app.database.dao.StudentAbsenceDao
import com.smart.resources.schools_app.database.model.ExamModel
import com.smart.resources.schools_app.database.model.LibraryModel
import com.smart.resources.schools_app.database.model.StudentAbsenceModel
import com.smart.resources.schools_app.databinding.FragmentRecyclerBinding
import com.smart.resources.schools_app.ui.activity.SectionActivity
import com.smart.resources.schools_app.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerBinding

    companion object {
        private const val EXTRA_SECTION = "extraSection"

        fun newInstance(section: Section): RecyclerFragment {
            val fragment = RecyclerFragment()
            Bundle().apply {
                putSerializable(EXTRA_SECTION, section)
                fragment.arguments = this
            }

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecyclerBinding.inflate(inflater, container, false)

        mapSectionToRecycler()
        return binding.root
    }

    private fun mapSectionToRecycler() {

        var stringId= 0

        when (arguments?.getSerializable(EXTRA_SECTION)) {
            Section.HOMEWORK -> {

                setupHomeworkRecycler()
                stringId= R.string.homework

            }
            Section.EXAM -> {
               setupExamRecycler()
                stringId= R.string.exams
            }

            Section.LIBRARY ->{

                setupLibraryRecycler()
                stringId= R.string.library
            }

            Section.NOTIFICATION ->{
                val adapter = NotificationRecyclerAdapter()
                binding.recyclerView.adapter= adapter
                stringId= R.string.notifications
            }
            Section.SCHEDULE -> {
                createGridLayout(ScheduleRecyclerAdapter())
                stringId= R.string.schedule
            }
            Section.ABSENCE -> {
                setupStudentAbsenceRecycler()
                stringId= R.string.absence
            }
        }

            (activity as SectionActivity).setCustomTitle(stringId)
    }


    private fun setupLibraryRecycler()=
        CoroutineScope(IO).launch{
            val libraryDao = BackendHelper.retrofit.create(LibraryDao::class.java)
            val result = MyResult.fromResponse(libraryDao.fetchLib())

            withContext(Main){
                when(result){
                    is Success -> {
                        if(result.data.isNullOrEmpty()){
                            binding.errorText.text= getString(R.string.no_homework)
                        }else {
                            val adapter = LibraryRecyclerAdapter(result.data)
                            createGridLayout(adapter)
                        }
                    }
                    is ResponseError -> binding.errorText.text= result.combinedMsg
                    is ConnectionError ->  binding.errorText.text= getString(R.string.connection_error)
                }

                binding.progressIndicator.hide()
            }
        }


    private fun setupHomeworkRecycler() =
        CoroutineScope(IO).launch{
            val homeworkDao = BackendHelper.retrofit.create(HomeworkDao::class.java)
            val result = MyResult.fromResponse(homeworkDao.fetchHomework())

            withContext(Main){
                when(result){
                    is Success -> {
                        if(result.data.isNullOrEmpty()){
                            binding.errorText.text= getString(R.string.no_homework)
                        }else {
                            val adapter = HomeworkRecyclerAdapter(result.data)
                            binding.recyclerView.adapter = adapter
                        }
                    }
                    is ResponseError -> binding.errorText.text= result.combinedMsg
                    is ConnectionError ->  binding.errorText.text= getString(R.string.connection_error)
                }

                binding.progressIndicator.hide()
            }
        }


    private fun setupExamRecycler()  =
        CoroutineScope(IO).launch{
            val examDao = BackendHelper.retrofit.create(ExamDao::class.java)
            val result = MyResult.fromResponse(examDao.fetchExam())

            withContext(Main){
                when(result){
                    is Success -> {
                        if(result.data.isNullOrEmpty()){
                            binding.errorText.text= getString(R.string.no_homework)
                        }else {
                            val adapter = ExamRecyclerAdapter(result.data)
                            binding.recyclerView.adapter = adapter
                        }
                    }
                    is ResponseError -> binding.errorText.text= result.combinedMsg
                    is ConnectionError ->  binding.errorText.text= getString(R.string.connection_error)
                }

                binding.progressIndicator.hide()
            }
        }

    private fun setupStudentAbsenceRecycler()  =
        CoroutineScope(IO).launch{
            val examDao = BackendHelper.retrofit.create(StudentAbsenceDao::class.java)
            val result = MyResult.fromResponse(examDao.fetchStudentAbsence())

            withContext(Main){
                when(result){
                    is Success -> {
                        if(result.data.isNullOrEmpty()){
                            binding.errorText.text= getString(R.string.no_homework)
                        }else {
                            val adapter = AbsenceRecyclerAdapter(result.data)
                            binding.recyclerView.adapter = adapter
                        }
                    }
                    is ResponseError -> binding.errorText.text= result.combinedMsg
                    is ConnectionError ->  binding.errorText.text= getString(R.string.connection_error)
                }

                binding.progressIndicator.hide()
            }
        }


    private fun createGridLayout(adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>) {
        binding.recyclerView.apply {
            val itemMargin = resources.getDimension(R.dimen.item_margin).toInt()
            setPadding(itemMargin, itemMargin, 0, itemMargin)
            layoutManager = GridLayoutManager(context, 2)
            this.adapter = adapter
        }
    }

}
