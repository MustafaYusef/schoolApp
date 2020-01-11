package com.smart.resources.schools_app.sharedUi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.databinding.ActivitySectionBinding
import com.smart.resources.schools_app.features.notification.NotificationFragment
import com.smart.resources.schools_app.core.myTypes.Section
import com.smart.resources.schools_app.core.myTypes.Section.EXAM
import com.smart.resources.schools_app.features.absence.AbsenceFragment
import com.smart.resources.schools_app.features.advertising.AdvertisingFragment
import com.smart.resources.schools_app.features.exam.AddMarkFragment
import com.smart.resources.schools_app.features.exam.ExamFragment
import com.smart.resources.schools_app.features.homework.HomeworkFragment
import com.smart.resources.schools_app.features.library.LibraryFragment
import com.smart.resources.schools_app.features.rating.AddRatingFragment
import com.smart.resources.schools_app.features.rating.RatingFragment
import com.smart.resources.schools_app.features.schedule.ScheduleFragment


class SectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySectionBinding

    companion object Factory{
        private const val EXTRA_SECTION= "extraSection"

        fun newInstance(context: Context, section: Section?){
            val intent= Intent(context, SectionActivity::class.java)
            intent.putExtra(EXTRA_SECTION, section)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_section)
        setSupportActionBar(binding.toolbar)

        createFragment()
    }


    fun setCustomTitle(title: String){
        binding.titleText.text= title
    }

    fun setCustomTitle(@StringRes title: Int){
        setCustomTitle(getString(title))
    }

    private fun createFragment() {
        when(intent.getSerializableExtra(EXTRA_SECTION) as Section){
            EXAM -> AddMarkFragment.newInstance(supportFragmentManager)
            Section.HOMEWORK -> HomeworkFragment.newInstance(supportFragmentManager)
            Section.NOTIFICATION -> NotificationFragment.newInstance(supportFragmentManager)
            Section.LIBRARY -> LibraryFragment.newInstance(supportFragmentManager)
            Section.SCHEDULE -> ScheduleFragment.newInstance(supportFragmentManager)
            Section.ADVERTISING -> AdvertisingFragment.newInstance(supportFragmentManager)
            Section.ABSENCE -> AbsenceFragment.newInstance(supportFragmentManager)
            Section.RATING -> AddRatingFragment.newInstance(supportFragmentManager)
        }
    }

}
