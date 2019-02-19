package com.cravefood.crave_calculator_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cravefood.cravecalculator.CalculatorFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(
            R.id.frameLayout,
            CalculatorFragment.newInstance()
        ).commit()
    }
}
