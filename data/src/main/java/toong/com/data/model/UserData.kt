package toong.com.data.model

import com.google.gson.annotations.SerializedName

data class UserData(
        val id: Int,

        val name: String,

        @SerializedName("avatar_url")
        val avatarUrl: String
) {

}