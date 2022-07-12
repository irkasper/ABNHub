package com.abnamro.abnhub.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblRepos")
data class RepoModel(

    @PrimaryKey
    val name: String,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    val description: String?,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    val visibility: String,

    @ColumnInfo(name = "is_private")
    val isPrivate: Boolean,
)
