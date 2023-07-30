package com.example.module_3_lesson_9_hw_1_compose.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.module_3_lesson_9_hw_1_compose.R
import com.example.module_3_lesson_9_hw_1_compose.ui.MainViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModelMain: MainViewModel,
    onLoginClicked: () -> Unit,
    onSignUpClicked: () -> Unit
) {
    var inputLogin by remember { mutableStateOf("") }
    var isUsernameEmpty by remember { mutableStateOf(false) }
    var inputPassword by remember { mutableStateOf("") }
    var isPasswordEmpty by remember { mutableStateOf(false) }

    var isCheckedRememberMe by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val snackbarError by viewModelMain.snackbarError.collectAsState()
    val currentUser by viewModelMain.currentUser.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    if (snackbarError != null) {
        Snackbar(
            action = {
                TextButton(onClick = { viewModelMain.snackbarError.value = null }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            },
        ) { Text(text = snackbarError!!) }
        LaunchedEffect(key1 = snackbarError) {
            delay(3_000)
            viewModelMain.snackbarError.value = null
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.login_title))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            value = inputLogin,
            onValueChange = {
                inputLogin = it

                isUsernameEmpty = it.isEmpty()
            },
            isError = isUsernameEmpty,
            label = { Text(text = stringResource(id = R.string.username)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_medium)),
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.8f),
            value = inputPassword,
            onValueChange = {
                inputPassword = it

                isPasswordEmpty = it.isEmpty()
            },
            isError = isPasswordEmpty,
            label = { Text(text = stringResource(id = R.string.password)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_medium)),
        )

        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isCheckedRememberMe,
                onCheckedChange = {
                    isCheckedRememberMe = it
                }
            )
            Text(text = stringResource(id = R.string.remember_me))
        }
        

        Button(
            modifier = Modifier.fillMaxWidth(0.5f),
            onClick = {

                viewModelMain.login(username = inputLogin, password = inputPassword)

                coroutineScope.launch {
                    delay(1_000)
                    if (currentUser != "") {
                        onLoginClicked()
                    }
                }
            },
            enabled = inputLogin.isNotEmpty() && inputPassword.isNotEmpty()
        ) { Text(text = stringResource(id = R.string.sign_in)) }
        Button(
            modifier = Modifier.fillMaxWidth(0.5f),
            onClick = onSignUpClicked,
        ) { Text(text = stringResource(id = R.string.sign_up)) }
    }
}