package com.flashcards.flashcards.service.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WordsResponse() : Parcelable {

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("context")
    @Expose
    var context: String? = null

    @SerializedName("isRemember")
    @Expose
    var isRemember: Boolean? = null

    @SerializedName("_id")
    @Expose
    var _id: String? = null

    @SerializedName("englishTitle")
    @Expose
    var englishTitle: String? = null

    @SerializedName("vietnameseTitle")
    @Expose
    var vietnameseTitle: String? = null

    @SerializedName("example")
    @Expose
    var example: String? = null

    @SerializedName("dateCreated")
    @Expose
    var dateCreated: String? = null

    constructor(parcel: Parcel) : this() {
        image = parcel.readString()
        type = parcel.readString()
        context = parcel.readString()
        isRemember = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        _id = parcel.readString()
        englishTitle = parcel.readString()
        vietnameseTitle = parcel.readString()
        example = parcel.readString()
        dateCreated = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(type)
        parcel.writeString(context)
        parcel.writeValue(isRemember)
        parcel.writeString(_id)
        parcel.writeString(englishTitle)
        parcel.writeString(vietnameseTitle)
        parcel.writeString(example)
        parcel.writeString(dateCreated)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WordsResponse> {
        override fun createFromParcel(parcel: Parcel): WordsResponse {
            return WordsResponse(parcel)
        }

        override fun newArray(size: Int): Array<WordsResponse?> {
            return arrayOfNulls(size)
        }
    }
}