package com.smart.resources.schools_app.core.bindingAdapters.textView

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.haytham.coder.extensions.hide
import com.haytham.coder.extensions.unicodeWrap
import com.smart.resources.schools_app.R
import org.threeten.bp.Duration

@BindingAdapter("android:setMark")
fun setMark(tv: TextView, mark: Int?) {
    tv.text = mark?.toString() ?: ""
}

@BindingAdapter("android:numberOfQuestions")
fun TextView.setNumberOfQuestions(numberOfQuestions: Int?) {
    text = context.getString(R.string.questions_number, numberOfQuestions ?: 0)
}

@BindingAdapter("android:durationInMinutes")
fun TextView.setDurationInMinutes(duration: Duration?) {
    text = context.getString(R.string.minutes_count, duration?.toMinutes()?.toInt() ?: 0)
}


@SuppressLint("SetTextI18n")
@BindingAdapter("android:timeInMinutesSeconds")
fun TextView.setTimeInMinutesSeconds(time: Duration?) {
    if(time == null) return

    val minutes = time.toMinutes()
    val remainingSeconds = time.seconds - (minutes * 60)
    text = "%02d:%02d".format(minutes, remainingSeconds)

}

@BindingAdapter("android:unorderedList")
fun TextView.setUnOrderedList(list: List<String>) {
    if(list.isEmpty()) {
        hide()
        return
    }
    text = list.joinToString(separator = "\n") { it }
}

@BindingAdapter("android:textCommaSeparated")
fun TextView.setText(list: List<String>?) {
    text = list.orEmpty().joinToString(separator = "، ") { it }.unicodeWrap()
}

@BindingAdapter("android:verticalNumber", "android:verticalLabel", requireAll = true)
fun TextView.setVerticalLabeledNumber(number:Int?, label:String?) {
    text=String.format("%d\n%s", number?:0, label)
}



