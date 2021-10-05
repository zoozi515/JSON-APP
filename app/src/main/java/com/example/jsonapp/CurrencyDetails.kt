package com.example.jsonapp

import com.google.gson.annotations.SerializedName

class CurrencyDetails {
    @SerializedName("date")
    var date: String? = null

    @SerializedName("eur")
    var eur: Datum? = null

    class Datum {
        @SerializedName("ada")
        var ada: String? = null

        @SerializedName("bam")
        var bam: String? = null

        @SerializedName("cad")
        var cad: String? = null

        @SerializedName("djf")
        var djf: String? = null

        @SerializedName("egp")
        var egp: String? = null

        @SerializedName("fjd")
        var fjd: String? = null
    }
}