package com.example.jsonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var main_constraintLayout : ConstraintLayout
    private lateinit var date_textView : TextView
    private lateinit var currency_editText : EditText
    private lateinit var spinner : Spinner
    private lateinit var convert_button : Button
    private lateinit var converted_textView : TextView

    private lateinit var date: String
    private var ada: Double = 0.0
    private var bam: Double = 0.0
    private var cad: Double = 0.0
    private var djf: Double = 0.0
    private var egp: Double = 0.0
    private var fjd: Double = 0.0
    private var converted_currency: Double = 0.0
    private var selected_currency: Double = 0.0

    val currency_options: Array<String> = arrayOf("Cardano (ADA)","The Bosnia-Herzegovina Convertible Mark (BAM)",
        "Canadian Dollar (CAD)","The Djiboutian Franc (DJF)", "Egyptian Pound (EGP)", "Fiji Dollar (FJD)")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_constraintLayout = findViewById(R.id.main_constraintLayout)
        date_textView = findViewById(R.id.date_textView)
        currency_editText = findViewById(R.id.currency_editText)
        spinner = findViewById(R.id.spinner)
        convert_button = findViewById(R.id.convert_button)
        converted_textView = findViewById(R.id.converted_textView)

        getJSONData()

        convert_button.setOnClickListener(){
            if (selected_currency == 0.0){
                Snackbar.make(main_constraintLayout, "You Should Select a Currency", Snackbar.LENGTH_SHORT).show()
            } else {
                if(currency_editText.getText().toString().trim().equals("")){
                    Snackbar.make(main_constraintLayout, "You Should Write a Value to be Converted", Snackbar.LENGTH_SHORT).show()
                }else {
                    converted_currency = currency_editText.text.toString()!!.toDouble()!!
                    converted_currency = converted_currency * selected_currency
                    converted_textView.text = "${converted_currency.toString()}"
                }
            }
        }

        getSelectedItem()
    }

    fun getSelectedItem(){
        val currencyOptions_adapetr = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,currency_options)
        spinner.adapter = currencyOptions_adapetr
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position){
                    0 -> selected_currency = ada
                    1 -> selected_currency = bam
                    2 -> selected_currency = cad
                    3 -> selected_currency = djf
                    4 -> selected_currency = egp
                    5 -> selected_currency = fjd
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Snackbar.make(main_constraintLayout, "You Should select a currency", Snackbar.LENGTH_SHORT).show()
            }
        }
    }


    fun getJSONData() {
        val apiInteface = APIClient().getClient()?.create(APIInterface::class.java)

        val call: Call<CurrencyDetails?>? = apiInteface!!.doGetListRecources()
        call?.enqueue(object : Callback<CurrencyDetails?> {
            override fun onResponse(
                call: Call<CurrencyDetails?>,
                response: Response<CurrencyDetails?>
            ) {
                var displayResponse = ""
                val resource: CurrencyDetails? = response.body()
                date = resource?.date.toString()
                val datumList = resource?.eur

                ada = datumList?.ada!!.toDouble()!!
                bam = datumList?.bam!!.toDouble()!!
                cad = datumList?.cad!!.toDouble()!!
                djf = datumList?.djf!!.toDouble()!!
                egp = datumList?.egp!!.toDouble()!!
                fjd = datumList?.fjd!!.toDouble()!!

                date_textView.text = "Date: ${date.toString()}"
            }

            override fun onFailure(call: Call<CurrencyDetails?>, t: Throwable) {
                converted_textView.text = "Calling failed: ${t.message}"
                call.cancel()
            }
        })
    }

}