package com.example.module_3_lesson_9_hw_1_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.module_3_lesson_9_hw_1_compose.ui.MainViewModel
import com.example.module_3_lesson_9_hw_1_compose.ui.theme.Grey10
import com.example.module_3_lesson_9_hw_1_compose.ui.theme.Grey20
import com.example.module_3_lesson_9_hw_1_compose.ui.theme.Module_3_Lesson_9_hw_1_ComposeTheme
import com.example.module_3_lesson_9_hw_1_compose.ui.theme.Purple40

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Module_3_Lesson_9_hw_1_ComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(
    viewModelMain: MainViewModel = viewModel()
) {
    val messagesListViewModel by viewModelMain.messagesList.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val listState = rememberLazyListState()

    LaunchedEffect(key1 = messagesListViewModel.size) {
        if (messagesListViewModel.isNotEmpty()) {
            listState.animateScrollToItem(index = messagesListViewModel.size - 1)
        }
    }

    Box() {
        Image(
            painter = painterResource(id = R.drawable.clouds),
            contentDescription = "background image",
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Grey10)
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.padding_medium),
                        vertical = dimensionResource(id = R.dimen.padding_s_m)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    modifier = Modifier
                        .scale(1.25f),
                    imageVector = Icons.Default.Clear,
                    contentDescription = "clear messages",
                )
                Text(
                    text = "Brother",
                    style = MaterialTheme.typography.headlineSmall
                )
                Image(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.padding_xlarge))
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.wiz_khalifa_1),
                    contentDescription = "avatar"
                )
            }


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                state = listState,
                horizontalAlignment = Alignment.Start
            ) {
                itemsIndexed(messagesListViewModel) { index, item ->
                    Card(
                        modifier = Modifier
                            .padding(
                                vertical = dimensionResource(id = R.dimen.padding_xsmall),
                                horizontal = dimensionResource(id = R.dimen.padding_small)
                            ),
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_medium)),
                        colors = CardDefaults.cardColors(Color.White),
                        border = BorderStroke(dimensionResource(id = R.dimen.thickness_divider), Grey10),
                        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.padding_xsmall))
                    ) {
                        Text(
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_s_m)),
                            text = item
                        )
                    }
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(id = R.dimen.padding_medium))
                    .weight(0.125f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    value = inputText,
                    onValueChange = {
                        inputText = it
                    },
                    label = { Text(text = stringResource(id = R.string.message)) },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_large)),
                )
                IconButton(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.padding_large))
                        .clip(CircleShape)
                        .background(Purple40),
                    onClick = {
                        viewModelMain.sendMessage(inputText)
                        inputText = ""
                    },
                    enabled = inputText.isNotEmpty()
                ) {
                    Icon(
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.padding_large)),
                        painter = painterResource(id = R.drawable.baseline_arrow_upward_48),
                        contentDescription = "send message",
                        tint = Color.White
                    )
                }

            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Module_3_Lesson_9_hw_1_ComposeTheme {
        MyApp()
    }
}