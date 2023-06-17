package com.example.mercadomovel.model

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class Util {
    companion object {
        fun formatarData(valor: String): String {
            val cleanString = valor.replace("[^\\d.]".toRegex(), "")
            val formattedString = StringBuilder()

            var index = 0
            for (i in 0 until cleanString.length) {
                val digit = cleanString[i]
                if (index == 2 || index == 5) {
                    formattedString.append("-")
                }
                formattedString.append(digit)
                index++
            }

            return formattedString.toString()
        }
        fun formatarParaDinheiro(valor: Double): String{
            val format = DecimalFormat("#.###,00")
            format.isDecimalSeparatorAlwaysShown = false

            return format.format(this).toString()
        }

        fun converterParaDouble(valorFormatado: String): Double {
            val cleanString = valorFormatado.replace("[R$,.\\s]".toRegex(), "")
            return if (cleanString.isNotEmpty()) {
                cleanString.toDouble() / 100
            } else {
                0.0
            }
        }
    }
}


fun Double.formatarParaDinheiro(): String {
    if (this == 0.0) {
        return "0.00"
    }

    val format = DecimalFormat("#,###.00")
    format.isDecimalSeparatorAlwaysShown = false
    return format.format(this).toString()
}

fun EditText.formatarParaDinheiro() {
    val df: DecimalFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR")) as DecimalFormat
    df.minimumFractionDigits = 2  // Define o número mínimo de casas decimais como 2
    df.isDecimalSeparatorAlwaysShown = true  // Exibe sempre o separador decimal
    var current = ""

    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.toString() != current) {
                removeTextChangedListener(this)

                val cleanString = if (s.toString().matches("^.*\\.\\d$".toRegex())) {
                    s.toString().replace(
                        "[R$,.\\s]".toRegex(),
                        ""
                    ) + "0"  // Adiciona um zero ao final se terminar com uma casa decimal
                } else {
                    s.toString().replace("[R$,.\\s]".toRegex(), "")
                }

                val parsed: Double
                parsed = if (cleanString.isNotEmpty()) {
                    val decimalValue =
                        cleanString.toDouble() / 100  // Dividir por 10 se tiver apenas uma casa decimal
                    BigDecimal(decimalValue).setScale(2, RoundingMode.HALF_UP).toDouble()
                } else {
                    0.0
                }

                val formatted = df.format(parsed)
                current = formatted
                setText(formatted)
                setSelection(formatted.length)

                addTextChangedListener(this)
            }
        }

        override fun afterTextChanged(s: Editable) {}
    })
}

fun String.converterParaDouble(): Double {
    val cleanString = this.replace("[R$,Unidade,.\\s]".toRegex(), "")
    return if (cleanString.isNotEmpty()) {
        cleanString.toDouble() / 100
    } else {
        0.0
    }
}

class MaskedEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {
    private val mask = "##-##-####"
    private var isUpdating = false

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable) {
                if (isUpdating) {
                    isUpdating = false
                    return
                }

                val s = editable.toString()
                val clean = s.replace("[^\\d.]".toRegex(), "")
                val cleanLength = clean.length

                if (cleanLength == 0 || cleanLength > mask.length) {
                    editable.clear()
                    return
                }

                var index = 0
                val masked = StringBuilder()

                for (i in 0 until mask.length) {
                    val maskChar = mask[i]
                    val isDigit = Character.isDigit(clean.getOrNull(index) ?: '0')

                    if (maskChar == '#' && isDigit || maskChar != '#') {
                        masked.append(if (isDigit) clean[index++] else maskChar)
                    } else {
                        break
                    }
                }

                isUpdating = true
                setText(masked.toString())
                setSelection(masked.length)
            }
        })
    }
}

fun EditText.formatarData() {
    val mask = "##/##/######"
    val maxCharacterCount = mask.count { it == '#' }
    val maskedWatcher = object : TextWatcher {
        private var isUpdating = false
        private var lastText = ""

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (isUpdating) {
                return
            }

            val enteredText = s.toString()
            val clean = enteredText.replace("[^\\d.]".toRegex(), "")
            val formatted = buildString {
                var index = 0
                for (i in 0 until mask.length) {
                    val maskChar = mask[i]
                    if (maskChar == '#') {
                        if (index < clean.length) {
                            append(clean[index])
                            index++
                        }
                    } else {
                        append(maskChar)
                    }
                }
            }

            if (formatted != lastText) {
                isUpdating = true
                setText(formatted)
                setSelection(formatted.length)
                isUpdating = false
            }

            lastText = formatted

            if (s?.length ?: 0 > maxCharacterCount) {
                text?.delete(maxCharacterCount, s?.length ?: 0)
            }
        }
    }

    addTextChangedListener(maskedWatcher)
}