package com.example.module_3_lesson_9_hw_1_compose.ui.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModelMain: MainViewModel,
    onCreateClicked: () -> Unit,
    onCancelCLicked: () -> Unit
) {
    var inputLogin by remember { mutableStateOf("") }
    var isUsernameEmpty by remember { mutableStateOf(false) }
    var inputPassword by remember { mutableStateOf("") }
    var isPasswordEmpty by remember { mutableStateOf(false) }



    val focusManager = LocalFocusManager.current


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.create_account_title))
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
        Button(
            modifier = Modifier.fillMaxWidth(0.5f),
            onClick = {
                viewModelMain.createUser(username = inputLogin, password = inputPassword)
                onCreateClicked()
            },
            enabled = inputLogin.isNotEmpty() && inputPassword.isNotEmpty()
        ) { Text(text = stringResource(id = R.string.create_account)) }
        Button(
            modifier = Modifier.fillMaxWidth(0.5f),
            onClick = onCancelCLicked
        ) { Text(text = stringResource(id = R.string.cancel)) }

    }
}