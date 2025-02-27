package com.smcdeveloper.nobinoapp.data.model.payment


import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("data")
    val paymentResponse: PaymentResponseData?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("timestamp")
    val timestamp: Long?
)
{
    data class PaymentResponseData(
        @SerializedName("createdAt")
        val createdAt: Long?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("payment")
        val payment: Payment?,
        @SerializedName("plan")
        val plan: Plan?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("user")
        val user: User?
    ) {
        data class Payment(
            @SerializedName("createdAt")
            val createdAt: Long?,
            @SerializedName("description")
            val description: String?,
            @SerializedName("discount")
            val discount: Discount?,
            @SerializedName("finalPrice")
            val finalPrice: Int?,
            @SerializedName("gateway")
            val gateway: String?,
            @SerializedName("id")
            val id: String?,
            @SerializedName("price")
            val price: Int?,
            @SerializedName("redirect")
            val redirect: String?,
            @SerializedName("status")
            val status: String?
        ) {
            class Discount
        }

        data class Plan(
            @SerializedName("description")
            val description: String?,
            @SerializedName("finalPrice")
            val finalPrice: Int?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("planId")
            val planId: Int?,
            @SerializedName("price")
            val price: Int?,
            @SerializedName("type")
            val type: String?
        )

        data class User(
            @SerializedName("email")
            val email: String?,
            @SerializedName("firstName")
            val firstName: String?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("mobile")
            val mobile: String?,
            @SerializedName("username")
            val username: String?
        )
    }
}