package toong.com.data.model

import com.google.gson.annotations.SerializedName
import toong.com.domain.entity.Repo

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