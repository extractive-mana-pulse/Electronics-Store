package com.example.e_shop.profile.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_shop.R
import com.example.e_shop.core.extensions.toastMessage
import com.example.e_shop.navigation.screens.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreen(
    navController: NavController = rememberNavController()
) {
    val context = LocalContext.current
    Scaffold()
    { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .size(115.dp)
                        .padding(16.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.inversePrimary)
                        .align(Alignment.CenterHorizontally)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "John Doe",
                            modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = FontFamily(Font(R.font.gabarito_bold)),
                                fontSize = 16.sp
                            )
                        )
                        Text(
                            text = "john.mclean@examplepetstore.com",
                            modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = FontFamily(Font(R.font.poppins_light)),
                                fontSize = 16.sp
                            )
                        )
                    }
                    Text(
                        text = "Edit",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                toastMessage(
                                    context = context,
                                    message = "Edit profile page"
                                )
                            }
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = FontFamily(Font(R.font.gabarito_bold)),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 12.sp
                        )
                    )
                }
                ProfilePageItem(
                    icon = ImageVector.vectorResource(id = R.drawable.shopping_bag),
                    contentDescription = "Localized description",
                    context = "My orders",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(56.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    onClick = {
                        toastMessage(
                            context = context,
                            message = "My orders"
                        )
                    }
                )
                ProfilePageItem(
                    icon = ImageVector.vectorResource(id = R.drawable.discount),
                    contentDescription = "Localized description",
                    context = "My discounts",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(56.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    onClick = {
                        toastMessage(
                            context = context,
                            message = "My discounts"
                        )
                    }
                )
                ProfilePageItem(
                    icon = Icons.Outlined.LocationOn,
                    contentDescription = "Localized description",
                    context = "Address",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(56.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    onClick = {
                        toastMessage(
                            context = context,
                            message = "Address page"
                        )
                    }
                )
                ProfilePageItem(
                    icon = Icons.Default.FavoriteBorder,
                    contentDescription = "Localized description",
                    context = "Wishlist",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(56.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    onClick = {
                        toastMessage(
                            context = context,
                            message = "Wishlist page"
                        )
                    }
                )
                ProfilePageItem(
                    icon = ImageVector.vectorResource(R.drawable.payment),
                    contentDescription = "Localized description",
                    context = "Payments",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(56.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    onClick = {
                        toastMessage(
                            context = context,
                            message = "Payment page"
                        )
                    }
                )
                ProfilePageItem(
                    icon = Icons.Outlined.Settings,
                    contentDescription = "Localized description",
                    context = "Settings",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(56.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    onClick = {
                        navController.navigate(Screens.Settings.route)
                    }
                )
                TextButton(
                    onClick = {
                        toastMessage(
                            context = context,
                            message = "Signed out"
                        )
                    },
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    content = {
                        Text(
                            text = "Sign out",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily(Font(R.font.gabarito_bold)),
                                color = MaterialTheme.colorScheme.error
                            )
                        )
                    }
                )
            }
        }
    }
}


@Composable
fun ProfilePageItem(
    icon: ImageVector,
    contentDescription: String,
    context: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = context,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}