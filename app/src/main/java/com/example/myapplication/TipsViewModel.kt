package com.example.myapplication

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.NumberFormat
import kotlin.math.ceil

class TipsViewModel : ViewModel() {
    private val _tipsState = MutableStateFlow(TipsState())
    private var _amountInput = MutableStateFlow("")
    private var _tipInput = MutableStateFlow("")
    private var _roundUp = MutableStateFlow(false)

    val tipsState = _tipsState.asStateFlow()
    val amountInput = _amountInput.asStateFlow()
    val tipInput = _tipInput.asStateFlow()
    val roundUp = _roundUp.asStateFlow()

    fun setAmount(amount: String) {
        _amountInput.value = amount
        updateTips()
    }

    fun setTip(tip: String) {
        _tipInput.value = tip
        updateTips()
    }

    fun setRoundUpd(roundUp: Boolean) {
        _roundUp.value = roundUp
        updateTips()
    }

    private fun updateTips(
    ) {
        val amount = amountInput.value.toDoubleOrNull() ?: 0.0
        val tipPercent = tipInput.value.toDoubleOrNull() ?: 0.0
        val result = calculateTip(amount, tipPercent, roundUp.value)
        _tipsState.value = TipsState(result)
    }

    @VisibleForTesting
    internal fun calculateTip(
        amount: Double,
        tipPercent: Double = 15.0,
        roundUp: Boolean
    ): Double {
        var tip = tipPercent / 100 * amount
        if (roundUp) {
            tip = ceil(tip)
        }
        return tip
    }

    data class TipsState(val _value: Double = 0.0) {
        val value: String = NumberFormat.getCurrencyInstance().format(_value)
    }
}