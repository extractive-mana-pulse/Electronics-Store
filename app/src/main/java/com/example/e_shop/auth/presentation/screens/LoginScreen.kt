package com.example.e_shop.auth.presentation.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.e_shop.R
import com.example.e_shop.navigation.screens.AuthScreens
import com.example.e_shop.navigation.screens.Graph

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreen(
    navController : NavHostController = rememberNavController()
) {
    var email by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontFamily = FontFamily(Font(R.font.gabarito_bold))
                        )
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
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
//              TODO("If valid email, show another text field with password and text forgot password.")
                Button(
                    onClick = { navController.navigate(Graph.HOME) },
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().height(56.dp)
                ) {
                    Text(text = "Continue")
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account?",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = FontFamily(Font(R.font.gabarito_variable_font_wght))
                        )
                    )
                    TextButton(
                        onClick = { navController.navigate(AuthScreens.Register.route) },
                    ) {
                        Text(
                            text = "Create one",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontFamily = FontFamily(Font(R.font.gabarito_bold))
                            )
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(16.dp).fillMaxWidth().height(56.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Google Logo",
                        modifier = Modifier.padding(end = 8.dp).size(24.dp)
                    )
                    Text(
                        text = "Continue with Google",
                        modifier = Modifier.padding(start = 8.dp).align(Alignment.CenterVertically)
                    )
                }
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().height(56.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.apple),
                        contentDescription = "Apple Logo",
                        modifier = Modifier.padding(end = 8.dp).size(24.dp)
                    )
                    Text(
                        text = "Continue with Apple",
                        modifier = Modifier.padding(start = 8.dp).align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}