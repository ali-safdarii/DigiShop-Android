package com.example.digishop.data.remote.responses.auth


import com.google.gson.annotations.SerializedName

data class UserApi(
  /*  @SerializedName("email")
    val email: String,

    @SerializedName("id")
    val id: Int,*/


        @SerializedName("activation_date")
        val activationDate: String?,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("current_team_id")
        val currentTeamId: String?,
        @SerializedName("deleted_at")
        val deletedAt: String?,
        @SerializedName("email")
        val email: String,
        @SerializedName("email_verified_at")
        val emailVerifiedAt: String?,
        @SerializedName("first_name")
        val firstName: String?,
        @SerializedName("id")
        val id: Int,
        @SerializedName("image")
        val image: String?,
        @SerializedName("last_name")
        val lastName: String?,
        @SerializedName("mobile")
        val mobile: String?,
        @SerializedName("mobile_verified_at")
        val mobileVerifiedAt: String?,
        @SerializedName("national_code")
        val nationalCode: String?,
        @SerializedName("profile_photo_url")
        val profilePhotoUrl: String,
        @SerializedName("slug")
        val slug: String?,
        @SerializedName("status")
        val status: Int,
        @SerializedName("two_factor_confirmed_at")
        val twoFactorConfirmedAt: String?,
        @SerializedName("updated_at")
        val updatedAt: String,
        @SerializedName("user_type")
        val userType: Int,
        @SerializedName("username")
        val username: String?
)

