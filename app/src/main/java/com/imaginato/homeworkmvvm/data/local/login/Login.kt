package com.imaginato.homeworkmvvm.data.local.login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Login")
data class Login (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var Id: Int = 0,

    @ColumnInfo(name = "userId")
    var userId: String,

    @ColumnInfo(name = "userName")
    var userName: String,

    @ColumnInfo(name = "isDeleted")
    var isDeleted: String,

    @ColumnInfo(name = "headerValue")
    var headerValue: String,

    )
