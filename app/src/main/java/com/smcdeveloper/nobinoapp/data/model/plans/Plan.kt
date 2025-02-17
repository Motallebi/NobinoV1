package com.smcdeveloper.nobinoapp.data.model.plans


import com.google.gson.annotations.SerializedName

data class Plan(
    @SerializedName("data")
    val planData: PlanData?
) {
    data class PlanData(
        @SerializedName("description")
        val description: String?,
        @SerializedName("duration")
        val duration: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("logo")
        val logo: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("price")
        val price: Price?,
        @SerializedName("status")
        val status: String?
    ) {
        data class Price(
            @SerializedName("amount")
            val amount: Int?,
            @SerializedName("discount")
            val discount: Discount?,
            @SerializedName("discountAmount")
            val discountAmount: Int?,
            @SerializedName("totalAmount")
            val totalAmount: Int?,
            @SerializedName("vat")
            val vat: Int?
        ) {
            data class Discount(
                @SerializedName("code")
                val code: String?,
                @SerializedName("id")
                val id: Int?,
                @SerializedName("name")
                val name: String?,
                @SerializedName("type")
                val type: String?,
                @SerializedName("value")
                val value: Double?
            )
        }
    }
}