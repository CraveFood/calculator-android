package com.cravefood.cravecalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.c_calc_fragment.*


class CalculatorFragment : Fragment() {

    private lateinit var viewModel: CalculatorViewModel
    private var onTotalListener: ((Double) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.c_calc_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configureObservables()
        configureListeners()
    }

    private fun configureObservables() {
        viewModel = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)

        viewModel.currentExpressionObservable.observe(this, Observer {
            textViewOperation.text = it
            focusOperationText()
        })

        viewModel.resultObservable.observe(this, Observer { total ->
            if (total != null) {
                textViewResult.text = getFormatResult(total)
                onTotalListener?.invoke(total)
            } else {
                textViewResult.text = ""
            }
        })
    }

    private fun getFormatResult(total: Double?) = total.toString().replace(".", viewModel.decimalSeparator)

    private fun configureListeners() {
        buttonPoint.text = viewModel.decimalSeparator
        buttonPoint.setOnClickListener { viewModel.addSeparator() }

        button0.setOnClickListener { viewModel.addNumber(NUMBER_0) }
        button1.setOnClickListener { viewModel.addNumber(NUMBER_1) }
        button2.setOnClickListener { viewModel.addNumber(NUMBER_2) }
        button3.setOnClickListener { viewModel.addNumber(NUMBER_3) }
        button4.setOnClickListener { viewModel.addNumber(NUMBER_4) }
        button5.setOnClickListener { viewModel.addNumber(NUMBER_5) }
        button6.setOnClickListener { viewModel.addNumber(NUMBER_6) }
        button7.setOnClickListener { viewModel.addNumber(NUMBER_7) }
        button8.setOnClickListener { viewModel.addNumber(NUMBER_8) }
        button9.setOnClickListener { viewModel.addNumber(NUMBER_9) }

        buttonClear.setOnClickListener { viewModel.removeLastIndex() }
        buttonClear.setOnLongClickListener {
            viewModel.clearExpression()

            return@setOnLongClickListener true
        }

        imageButtonDivide.setOnClickListener { viewModel.addOperator(OPERATOR_DIVIDE) }
        imageButtonTimes.setOnClickListener { viewModel.addOperator(OPERATOR_TIMES) }
        imageButtonMinus.setOnClickListener { viewModel.addOperator(OPERATOR_MINUS) }
        imageButtonPlus.setOnClickListener { viewModel.addOperator(OPERATOR_PLUS) }

        imageButtonEqual.setOnClickListener { viewModel.getResult() }
    }

    private fun focusOperationText() {
        horizontalScrollView.postDelayed(
            Runnable { horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT) },
            100L
        )
    }

    companion object {
        fun newInstance(onTotalListener: ((Double) -> Unit)? = null): CalculatorFragment {
            val fragment = CalculatorFragment()
            fragment.onTotalListener = onTotalListener
            return fragment
        }
    }
}
