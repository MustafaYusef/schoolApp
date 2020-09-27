package com.smart.resources.schools_app.core.bindingAdapters.textView

import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.orhanobut.logger.Logger
import com.smart.resources.schools_app.core.adapters.dateDisFormatter
import com.smart.resources.schools_app.core.extentions.activity
import com.smart.resources.schools_app.sharedUi.fragment.DatePickerFragment
import org.threeten.bp.LocalDate

private const val SET_DATE_ATTRIBUTE = "android:setDate"
private const val SHOW_DATE_PICKER_ON_CLICK_ATTRIBUTE = "android:showDatePickerOnClick"

@BindingAdapter(SET_DATE_ATTRIBUTE)
fun TextView.setDate(date: LocalDate?) {
    try {
        text = date?.format(dateDisFormatter)
    } catch (e: Exception) {
        Logger.e("binding error: ${e.message}")
    }
}

@InverseBindingAdapter(attribute = SET_DATE_ATTRIBUTE)
fun TextView.toLocalDate(): LocalDate {
    return LocalDate.parse(
        text,
        dateDisFormatter
    )
}

@BindingAdapter("${SET_DATE_ATTRIBUTE}AttrChanged")
fun TextView.setDateChangedListener(
    attrChange: InverseBindingListener
) {
    doAfterTextChanged {
        attrChange.onChange()
    }
}

@BindingAdapter(SHOW_DATE_PICKER_ON_CLICK_ATTRIBUTE)
fun TextView.setShowDatePickerOnClick(enabled: Boolean) {
    if (enabled) {
        setOnClickListener {
            val pickerFragment = DatePickerFragment.newInstance()
            pickerFragment.onDateSet = {
                setDate(it)
            }
            context.activity?.let {
                pickerFragment.show(it.supportFragmentManager, "")
            }
        }
    } else {
        setOnClickListener(null)
    }
}