package vn.linh.data.model

import com.google.gson.annotations.SerializedName
import vn.linh.domain.entity.Repo

data class RepoData(
        val id: Int,

        val name: String,

        @SerializedName("full_name")
        val fullName: String
) {
    fun mapToDomain() : Repo = Repo(
            name = name
    )
}