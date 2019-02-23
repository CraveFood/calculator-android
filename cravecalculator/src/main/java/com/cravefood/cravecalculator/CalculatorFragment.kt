package com.cravefood.cravecalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.calculator_fragment.*


class CalculatorFragment : Fragment() {

    private lateinit var viewModel: CalculatorViewModel
    private var onCancelListener: (() -> Unit)? = null
    private var onConfirmListener: ((Double) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calculator_fragment, container, false)
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
        })

        viewModel.resultObservable.observe(this, Observer {
            if (it != null) {
                textViewResult.text = getFormatResult(it)

                buttonConfirm.isEnabled = it > 0.0
            } else {
                textViewResult.text = ""
                buttonConfirm.isEnabled = false
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

        buttonCancel.setOnClickListener { cancelTapped() }
        buttonConfirm.setOnClickListener { confirmTapped() }
    }

    private fun confirmTapped() {
        val total = viewModel.resultObservable.value ?: 0.0
        if (onConfirmListener != null) {
            onConfirmListener?.invoke(total)
        } else Toast.makeText(context, "Total: $total", Toast.LENGTH_SHORT).show()
    }

    private fun cancelTapped() {
        if (onCancelListener != null) onCancelListener?.invoke()
        else activity?.onBackPressed()
    }

    companion object {
        fun newInstance(
            cancelListener: (() -> Unit)? = null,
            confirmListener: ((Double) -> Unit)? = null
        ): CalculatorFragment {
            val fragment = CalculatorFragment()
            fragment.onCancelListener = cancelListener
            fragment.onConfirmListener = confirmListener
            return fragment
        }
    }
}
