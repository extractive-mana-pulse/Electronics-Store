package com.example.e_shop.auth.presentation.screens

import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.e_shop.R
import com.example.e_shop.auth.presentation.GoogleAuthUiClient
import com.example.e_shop.auth.presentation.vm.SignInViewModel
import com.example.e_shop.core.extensions.toastMessage
import com.example.e_shop.navigation.screens.AuthScreens
import com.example.e_shop.navigation.screens.Graph
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController : NavHostController = rememberNavController(),
    googleAuthUiClient: GoogleAuthUiClient
) {

    val context = LocalContext.current
    val viewModel: SignInViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        if(googleAuthUiClient.getSignedInUser() != null) {
            navController.navigate(Graph.HOME)
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if(result.resultCode == RESULT_OK) {
                scope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSignInResult(signInResult)
                }
            }
        }
    )
    LaunchedEffect(key1 = state.isSignInSuccessful) {
        Log.d("state", state.isSignInSuccessful.toString())
        if(state.isSignInSuccessful) {
            navController.navigate(Graph.HOME)
            toastMessage(
                context = context,
                message = "Sign in successful"
            )
            viewModel.resetState()
        } else {
            toastMessage(
                context = context,
                message = state.signInError ?: "Sign in failed"
            )
        }
    }

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
                /**"If valid email, show another text field with password and text forgot password."*/
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
                    onClick = {
                        scope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                        toastMessage(
                            context = context,
                            message = "Continue with google clicked"
                        )
                    },
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