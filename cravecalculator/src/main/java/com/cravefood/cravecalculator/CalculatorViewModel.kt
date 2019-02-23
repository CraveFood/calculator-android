package com.cravefood.cravecalculator

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormatSymbols

const val NUMBER_0 = 0
const val NUMBER_1 = 1
const val NUMBER_2 = 2
const val NUMBER_3 = 3
const val NUMBER_4 = 4
const val NUMBER_5 = 5
const val NUMBER_6 = 6
const val NUMBER_7 = 7
const val NUMBER_8 = 8
const val NUMBER_9 = 9
const val OPERATOR_DIVIDE = "/"
const val OPERATOR_TIMES = "*"
const val OPERATOR_MINUS = "-"
const val OPERATOR_PLUS = "+"

class CalculatorViewModel : ViewModel() {

    val decimalSeparator = DecimalFormatSymbols.getInstance().decimalSeparator.toString()
    val resultObservable = MutableLiveData<Double>()
    val currentExpressionObservable = MutableLiveData<String>()

    init {
        resultObservable.value = null
        currentExpressionObservable.value = ""
    }

    private fun isNumber(test: String): Boolean {
        if (test.isEmpty()) return false

        return when (test) {
            NUMBER_0.toString() -> true
            NUMBER_1.toString() -> true
            NUMBER_2.toString() -> true
            NUMBER_3.toString() -> true
            NUMBER_4.toString() -> true
            NUMBER_5.toString() -> true
            NUMBER_6.toString() -> true
            NUMBER_7.toString() -> true
            NUMBER_8.toString() -> true
            NUMBER_9.toString() -> true
            else -> false
        }
    }

    private fun isOperator(test: String): Boolean {
        return when (test) {
            OPERATOR_DIVIDE -> true
            OPERATOR_TIMES -> true
            OPERATOR_MINUS -> true
            OPERATOR_PLUS -> true
            else -> false
        }
    }

    private fun canAddNumber(): Boolean {
        return when {
            currentExpressionObservable.value == null -> true // If is null
            currentExpressionObservable.value?.isEmpty() == true -> true // If is empty
            isNumber(currentExpressionObservable.value?.last().toString()) -> true // If the last index is a number
            isOperator(currentExpressionObservable.value?.last().toString()) -> true // If the last index is a operator
            currentExpressionObservable.value?.last().toString() == decimalSeparator -> true // If the last index is a separator
            else -> false
        }
    }

    private fun canAddOperator(): Boolean {
        return when {
            currentExpressionObservable.value == null -> false
            currentExpressionObservable.value == "" -> false
            isNumber(currentExpressionObservable.value!!.last().toString()) -> true // If the last index is a number
            else -> false
        }
    }

    private fun canAddSeparator(): Boolean {
        return when {
            currentExpressionObservable.value == null -> false
            currentExpressionObservable.value == "" -> false
            isOperator(currentExpressionObservable.value!!.last().toString()) -> false // If the last index is a operator
            lastNumberHasSeparator(currentExpressionObservable.value ?: "") -> false // If the last index is a operator
            isNumber(currentExpressionObservable.value!!.last().toString()) -> true // If the last index is a number
            else -> false
        }
    }

    private fun lastNumberHasSeparator(test: String): Boolean {
        val regex = Regex("[+\\-*/]?([\\d.,]+)\$")
        val match = regex.find(test)

        return if (match != null) {
            val precision = match.value
            val count =
                precision.filter { it.toString() == decimalSeparator }.count() // Count decimalSeparator occurrences

            count > 0
        } else {
            false
        }
    }

    private fun lastIndexIsOperator(expression: String? = null): Boolean {
        return expression != null && expression != "" && isOperator(expression.last().toString())
    }

    private fun updatePreviewResult(newExpression: String) {
        if (newExpression.isNotEmpty()) {
            if (!lastIndexIsOperator(newExpression)) {
                val evaluate = ExpressionBuilder(replaceSeparator(newExpression))
                    .build()
                    .evaluate()

                resultObservable.value = "%.2f".format(evaluate).replace(",", ".").toDouble()
            }
        } else {
            resultObservable.value = 0.0
        }
    }

    private fun updateCurrentExpression(newExpression: String) {
        currentExpressionObservable.value = newExpression
    }

    private fun replaceSeparator(expression: String) = expression.replace(",", ".")

    fun addNumber(number: Int) {
        if (canAddNumber()) { // Add the number
            val newExpression = "${currentExpressionObservable.value ?: ""}$number"

            try {
                updatePreviewResult(newExpression)
                updateCurrentExpression(newExpression)
            } catch (e: Exception) {
                Log.e("Exception", e.message)
            }
        }
    }

    fun addOperator(operator: String) {
        when {
            canAddOperator() -> { // Add the operator
                updateCurrentExpression("${currentExpressionObservable.value ?: ""}$operator")
            }
            lastIndexIsOperator(currentExpressionObservable.value) -> { // Replace the last operator for the new operator
                var newExpression = currentExpressionObservable.value.toString()
                newExpression = "${newExpression.substring(0, newExpression.length - 1)}$operator"

                updateCurrentExpression(newExpression)
            }
        }
    }

    fun addSeparator() {
        if (canAddSeparator()) {
            updateCurrentExpression("${currentExpressionObservable.value ?: ""}$decimalSeparator")
        }
    }

    fun removeLastIndex() {
        var newExpression = currentExpressionObservable.value
        newExpression = if (newExpression != null && newExpression.isNotEmpty()) {
            newExpression.toString().let {
                it.substring(0, it.length - 1)
            }
        } else ""

        updatePreviewResult(newExpression)
        updateCurrentExpression(newExpression)
    }

    fun clearExpression() {
        updatePreviewResult("")
        updateCurrentExpression("")
    }

    fun getResult() {
        val total = resultObservable.value
        if (total != null) {
            updatePreviewResult(total.toString())
            updateCurrentExpression(total.toString())
        }
    }
}
