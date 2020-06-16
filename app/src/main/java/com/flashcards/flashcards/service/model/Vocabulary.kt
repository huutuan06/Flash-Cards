package com.flashcards.flashcards.service.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Vocabulary() : Parcelable {

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("context")
    @Expose
    var context: String? = null

    @SerializedName("englishTitle")
    @Expose
    var englishTitle: String? = null

    @SerializedName("vietnameseTitle")
    @Expose
    var vietnameseTitle: String? = null

    @SerializedName("example")
    @Expose
    var example: String? = null

    constructor(parcel: Parcel) : this() {
        image = parcel.readString()
        type = parcel.readString()
        context = parcel.readString()
        englishTitle = parcel.readString()
        vietnameseTitle = parcel.readString()
        example = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(type)
        parcel.writeString(context)
        parcel.writeString(englishTitle)
        parcel.writeString(vietnameseTitle)
        parcel.writeString(example)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Vocabulary> {
        override fun createFromParcel(parcel: Parcel): Vocabulary {
            return Vocabulary(parcel)
        }

        override fun newArray(size: Int): Array<Vocabulary?> {
            return arrayOfNulls(size)
        }
    }
}
