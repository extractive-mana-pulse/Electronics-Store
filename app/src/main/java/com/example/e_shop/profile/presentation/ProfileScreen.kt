package com.example.e_shop.profile.presentation

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.e_shop.R
import com.example.e_shop.navigation.screens.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreen(
    navController: NavController = rememberNavController()
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Profile")
                },
                actions = {
                    IconButton(onClick = {
                        Toast.makeText(
                            context,
                            "Edit profile",
                            Toast.LENGTH_SHORT
                        ).show() }) {
                        Icon(
                            imageVector = Icons.Filled.Create,
                            contentDescription = "Localized description"
                        )
                    }
                },

                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                scrollBehavior = scrollBehavior
            )
        }
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .height(105.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier
                            .size(105.dp)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(36.dp))
                            .background(MaterialTheme.colorScheme.inversePrimary)
                    )
                    Column {
                        Text(
                            text = "John Doe",
                            modifier = Modifier,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = "john.mclean@examplepetstore.com",
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                CustomRow(
                    icon = ImageVector.vectorResource(id = R.drawable.shopping_bag),
                    contentDescription = "Localized description",
                    context = "My orders",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(56.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    onClick = {
                        Toast.makeText(
                            context,
                            "My orders",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
                CustomRow(
                    icon = ImageVector.vectorResource(id = R.drawable.discount),
                    contentDescription = "Localized description",
                    context = "My discounts",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(50.dp)
                        .background(MaterialTheme.colorScheme.surface),
                    onClick = {
                        Toast.makeText(
                            context,
                            "My discounts",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
                Button(
                    onClick = {
                        navController.navigate(Screens.Home.route)
                        Toast.makeText(
                            context,
                            "Sign out",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    modifier = Modifier.fillMaxWidth().padding(16.dp),

                ) {
                    Text(text = "Sign out")
                }
            }
        }
    }
}


@Composable
fun CustomRow(
    icon: ImageVector,
    contentDescription: String,
    context: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
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
        )
    }
    HorizontalDivider(modifier = Modifier.padding(start = 16.dp))
}