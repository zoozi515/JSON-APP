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
    private var converted_currency: Double = 0.0

    val currency_options: Array<String> = arrayOf("ada","bam","cad","djf")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_constraintLayout = findViewById(R.id.main_constraintLayout)
        date_textView = findViewById(R.id.date_textView)
        currency_editText = findViewById(R.id.currency_editText)
        spinner = findViewById(R.id.spinner)
        convert_button = findViewById(R.id.convert_button)
        converted_textView = findViewById(R.id.converted_textView)

        val apiInteface = APIClient().getClient()?.create(APIInterface::class.java)

        val call: Call<CurrencyDetails?>? = apiInteface!!.doGetListRecources()
        call?.enqueue(object : Callback<CurrencyDetails?>{
            override fun onResponse(
                call: Call<CurrencyDetails?>,
                response: Response<CurrencyDetails?>
            ) {
                val resource: CurrencyDetails? = response.body()
                date = resource?.date.toString()
                ada = resource?.eur?.ada!!.toDouble()
                bam = resource?.eur?.bam!!
                cad = resource?.eur?.cad!!
                djf = resource?.eur?.djf!!
            }

            override fun onFailure(call: Call<CurrencyDetails?>, t: Throwable) {
                call.cancel()
            }
        })

        val currencyOptions_adapetr = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,currency_options)
        spinner.adapter = currencyOptions_adapetr
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var temp = currency_editText.text.toString()!!
                if(p2 == 0){
                    converted_currency = ada * (temp.toDouble()!!)
                } else if(p2 == 0){
                    converted_currency = bam * (temp.toDouble()!!)
                } else if(p2 == 0){
                    converted_currency = cad * (temp.toDouble()!!)
                } else{
                    converted_currency = djf * (temp.toDouble()!!)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Snackbar.make(main_constraintLayout, "You Should select a currency", Snackbar.LENGTH_SHORT).show()
            }
        }

        convert_button.setOnClickListener(){
            converted_textView.text = "${converted_currency.toString()}"
        }
    }
}