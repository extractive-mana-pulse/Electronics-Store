package com.example.e_shop.profile.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.e_shop.R
import com.example.e_shop.core.extensions.toastMessage
import com.example.e_shop.profile.domain.model.CardInfo
import com.example.e_shop.profile.presentation.vm.CardViewModel
import java.util.regex.Pattern

@Composable
fun AddPaymentScreen(
    navController: NavHostController = rememberNavController()
) {

    val context = LocalContext.current
    var cvv by remember { mutableStateOf("") }
    var cardBrandIcon by remember { mutableStateOf<Int?>(null) }
    val cvvCharLength = 3
    var exp by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var cardHolderNumber by remember { mutableStateOf("") }
    val cardViewModel: CardViewModel = hiltViewModel()

    LaunchedEffect(Unit) { cardViewModel.getCards() }

    val insertStatus by cardViewModel.insertStatus.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            AddPaymentScreenTopAppBar(
                navController = navController
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ){
            Column(
                modifier = Modifier
            ) {
                insertStatus?.let { insertedCard ->
                    toastMessage(
                        context = context,
                        message = "Card ${insertedCard.cardHolderName} successfully"
                    )
                    navController.popBackStack()
                }

                TextField(
                    value = cardNumber,
                    onValueChange = {
                        cardNumber = it
                        cardBrandIcon = when (getCreditCardType(it)) {
                            "VISA" -> R.drawable.visa
                            "MASTER" -> R.drawable.master_cards
                            else -> R.drawable.payment
                        }
                    },
                    label = { Text("Card number") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    visualTransformation = CardNumberMask(),
                    trailingIcon = {
                        cardBrandIcon?.let {
                            Image(
                                painter = painterResource(id = it),
                                contentDescription = null,
                                modifier = Modifier.size(48.dp).padding(top = 8.dp)
                            )
                        }
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextField(
                        value = cvv,
                        onValueChange = { if (it.length <= cvvCharLength) cvv = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("CVV") },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(8.dp)
                    )

                    TextField(
                        value = exp,
                        onValueChange = { exp = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Exp") },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        visualTransformation = ExpirationDateMask(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(8.dp)
                    )
                }

                TextField(
                    value = cardHolderNumber,
                    onValueChange = { cardHolderNumber = it },
                    label = { Text("Card holder name") },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true,
                )
            }

            Button(
                onClick = {
                    val newCardInfo = CardInfo(
                        number = cardNumber.toIntOrNull(),
                        expiryDate = exp,
                        cvv = cvv.toIntOrNull(),
                        cardHolderName = cardHolderNumber,
                        isDefault = false
                    )
                    cardViewModel.insertCard(newCardInfo)
                },

                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .align(Alignment.BottomCenter),
            ) {
                Text(
                    text = stringResource(R.string.save),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(R.font.gabarito_bold))
                    )
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AddPaymentScreenTopAppBar(navController: NavHostController) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Add Payment",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily(Font(R.font.gabarito_bold))
                )
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigateUp()
                },
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clip(CircleShape)
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                    contentDescription = "Navigate back to payment screen"
                )
            }
        }
    )
}


class CardNumberMask(private val separator: String = " ") : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return makeCardNumberFilter(text, separator)
    }

    private fun makeCardNumberFilter(text: AnnotatedString, separator: String): TransformedText {
        val formatOfMask = if (text.text.length >= 16) text.text.substring(0..15) else text.text
        var out = ""
        for (i in formatOfMask.indices) {
            out += formatOfMask[i]
            if (i == 3 || i == 7 || i == 11) out += separator
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return if (offset <= 3) offset
                else if (offset <= 7) offset + 1
                else if (offset <= 11) offset + 2
                else if (offset <= 16) offset + 3
                else 19
            }

            override fun transformedToOriginal(offset: Int): Int {
                return if (offset <= 4) offset
                else if (offset <= 9) offset - 1
                else if (offset <= 14) offset - 2
                else if (offset <= 19) offset - 3
                else 16
            }
        }

        return TransformedText(AnnotatedString(out), offsetMapping)
    }
}

class ExpirationDateMask : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return makeExpirationFilter(text)
    }

    private fun makeExpirationFilter(text: AnnotatedString): TransformedText {
        // format: XX/XX
        val trimmed = if (text.text.length >= 4) text.text.substring(0..3) else text.text
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i == 1) out += "/"
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 1) return offset
                if (offset <= 4) return offset + 1
                return 5
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 5) return offset - 1
                return 4
            }
        }

        return TransformedText(AnnotatedString(out), offsetMapping)
    }
}

fun getCreditCardType(creditCardNumber: String): String {
    val regVisa = Pattern.compile("^4[0-9]{12}(?:[0-9]{3})?$")
    val regMaster = Pattern.compile("^5[1-5][0-9]{14}$")
    val regExpress = Pattern.compile("^3[47][0-9]{13}$")
    val regDiners = Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9]{11}$")
    val regDiscover = Pattern.compile("^6(?:011|5[0-9]{2})[0-9]{12}$")
    val regJCB = Pattern.compile("^(?:2131|1800|35\\d{3})\\d{11}$")

    return when {
        regVisa.matcher(creditCardNumber).matches() -> "VISA"
        regMaster.matcher(creditCardNumber).matches() -> "MASTER"
        regExpress.matcher(creditCardNumber).matches() -> "AEXPRESS"
        regDiners.matcher(creditCardNumber).matches() -> "DINERS"
        regDiscover.matcher(creditCardNumber).matches() -> "DISCOVER"
        regJCB.matcher(creditCardNumber).matches() -> "JCB"
        else -> "invalid"
    }
}