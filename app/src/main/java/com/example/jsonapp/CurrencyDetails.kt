package com.example.jsonapp

import com.google.gson.annotations.SerializedName

class CurrencyDetails {
     @SerializedName("date")
    var date: String? = null

    @SerializedName("eur")
    var eur: Datum? = null

    class Datum {
        @SerializedName("ada")
        var ada: Double? = null

        @SerializedName("bam")
        var bam: Double? = null

        @SerializedName("cad")
        var cad: Double? = null

        @SerializedName("djf")
        var djf: Double? = null
    }
}