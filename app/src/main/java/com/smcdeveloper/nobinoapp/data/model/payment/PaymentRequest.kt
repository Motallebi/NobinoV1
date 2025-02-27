package com.smcdeveloper.nobinoapp.data.model.payment

import com.google.gson.annotations.SerializedName

data class PaymentRequest(

@SerializedName("discount")
val discount: Discount?,
@SerializedName("plan")
val plan: Plan?
) {
    data class Discount(
        @SerializedName("code")
        val code: String?
    )

    data class Plan(
        @SerializedName("id")
        val id: Int?
    )
}