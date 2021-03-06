package com.smart.resources.schools_app.features.rating

import android.os.Parcel
import android.os.Parcelable
import com.smart.resources.schools_app.features.students.models.StudentWithMark
import org.threeten.bp.LocalDate


class RatingModel  (
    val studentId: String,
    var name: String,
    var rate: Float= -1.0f,
    var note: String= "",
    var date: LocalDate= LocalDate.now()
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readFloat(),
        parcel.readString()?: "",
        parcel.readSerializable() as LocalDate
    )

    constructor(student: StudentWithMark):this(student.id, student.name)

    val isRated get() =  rate > -1.0F
    fun reset(){
        rate= -1.0F
        note= ""
        date= LocalDate.now()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(studentId)
        parcel.writeString(name)
        parcel.writeFloat(rate)
        parcel.writeString(note)
        parcel.writeSerializable(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "${super.toString()}\nAddRatingModel(studentId='$studentId', studentName='$name')"
    }


    companion object CREATOR : Parcelable.Creator<RatingModel> {
        override fun createFromParcel(parcel: Parcel): RatingModel {
            return RatingModel(parcel)
        }

        override fun newArray(size: Int): Array<RatingModel?> {
            return arrayOfNulls(size)
        }
    }
}