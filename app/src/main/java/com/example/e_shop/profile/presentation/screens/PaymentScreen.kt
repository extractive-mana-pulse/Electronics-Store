package com.example.e_shop.profile.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_shop.R
import com.example.e_shop.core.extensions.toastMessage
import com.example.e_shop.navigation.screens.Screens
import com.example.e_shop.profile.domain.model.CardInfo
import com.example.e_shop.profile.presentation.vm.CardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    navController: NavController = rememberNavController(),
) {
    val cardViewModel: CardViewModel = hiltViewModel()
    val cards by cardViewModel.cards.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            PaymentScreenTopAppBar(
                navController = navController
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.AddPayment.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new payment method"
                )
            }
        }
    ) { innerPadding ->
        Log.d("PaymentScreen", "Padding: $innerPadding, Cards size: ${cards.size}")
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                cards.isEmpty() -> {
                    Text(
                        text = "Add your payment method",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    PaymentMethods(
                        cards = cards,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun PaymentMethods(
    cards: List<CardInfo>,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Cards",
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = FontFamily(Font(R.font.gabarito_bold))
            )
        )

        LazyColumn {
            items(cards) { card ->
                PaymentCard(
                    cardInfo = card,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun PaymentCard(
    cardInfo: CardInfo,
    navController: NavController
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(
                onClick = {
                    toastMessage(
                        context = context,
                        message = "Implement logic to navigate to card details screen"
                    )
                }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = cardInfo.number?.toString()?.takeLast(4) ?: "****",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = " • ${cardInfo.number}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Text(
                    text = cardInfo.cardHolderName ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            Icon(
                painter = painterResource(R.drawable.payment),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PaymentScreenTopAppBar(navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Payment",
                style = MaterialTheme.typography.titleLarge.copy(
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
                    contentDescription = "Navigate back to profile page"
                )
            }
        }
    )
}