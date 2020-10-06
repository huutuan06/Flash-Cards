package com.flashcards.flashcards.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "vocabularies")
data class Vocabulary(
    @PrimaryKey
    @SerializedName("_id")
    var id: String = "",
    @SerializedName("image")
    @ColumnInfo(name = "image")
    var image: String = "",
    @SerializedName("type")
    @ColumnInfo(name = "type")
    var type: String = "",
    @SerializedName("context")
    @ColumnInfo(name = "context")
    var context: String = "",
    @SerializedName("englishTitle")
    @ColumnInfo(name = "english_title")
    var englishTitle: String = "",
    @SerializedName("vietnameseTitle")
    @ColumnInfo(name = "vietnamese_title")
    var vietnameseTitle: String = "",
    @SerializedName("example")
    @ColumnInfo(name = "example")
    var example: String = ""
) : Parcelable
