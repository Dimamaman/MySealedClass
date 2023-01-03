package com.example.mysealedclass

//noinspection SuspiciousImport
import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import com.example.mysealedclass.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private val currencies = listOf(Dollar(), Euro(), Crypto())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, currencies.map { it.name })

        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        binding.currency.adapter = adapter

        binding.convert.setOnClickListener {
            val low = currencyFromSelection()
            val high = currencyFromSelection()

            low.amount = binding.lowAmount.text.toString().toDouble()
            high.amount = binding.highAmount.text.toString().toDouble()

            binding.lowAmountInDollars.text = String.format("$%.2f", low.totalValueInDollars())
            binding.highAmountInDollars.text = String.format("$%.2f", high.totalValueInDollars())
        }
    }

    private fun currencyFromSelection() =
        when (currencies[binding.currency.selectedItemPosition]) {
            is Dollar -> Dollar()
            is Euro -> Euro()
            is Crypto -> Crypto()
        }
}