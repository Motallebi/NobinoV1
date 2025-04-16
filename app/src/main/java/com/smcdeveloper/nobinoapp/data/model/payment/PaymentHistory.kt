package com.smcdeveloper.nobinoapp.data.model.payment


import com.google.gson.annotations.SerializedName

data class PaymentHistory(
    @SerializedName("data")
    val paymentHistoryData: PaymentHistoryData?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("userMessage")
    val userMessage: String?
) {
    data class PaymentHistoryData(
        @SerializedName("items")
        val items: List<Payment?>?,
        @SerializedName("page")
        val page: Int?,
        @SerializedName("size")
        val size: Int?,
        @SerializedName("total")
        val total: Int?
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
            data class Discount(
                @SerializedName("code")
                val code: String?,
                @SerializedName("type")
                val type: String?,
                @SerializedName("value")
                val value: Double?
            )
        }
    }
}