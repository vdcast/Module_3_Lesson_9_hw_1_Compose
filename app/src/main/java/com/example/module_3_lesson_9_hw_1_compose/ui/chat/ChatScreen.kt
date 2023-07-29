package com.example.module_3_lesson_9_hw_1_compose.ui.chat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.module_3_lesson_9_hw_1_compose.R
import com.example.module_3_lesson_9_hw_1_compose.ui.MainViewModel
import com.example.module_3_lesson_9_hw_1_compose.ui.theme.Grey10
import com.example.module_3_lesson_9_hw_1_compose.ui.theme.Purple40
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModelMain: MainViewModel
) {
    val messagesListViewModelOld by viewModelMain.messagesListOld.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val listStateOld = rememberLazyListState()

    val currentUser by viewModelMain.currentUser.collectAsState()
    val messagesList by viewModelMain.messagesList.collectAsState()



    LaunchedEffect(key1 = messagesList.size) {
        if (messagesList.isNotEmpty()) {
            listStateOld.animateScrollToItem(index = messagesList.size - 1)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
                        .size(dimensionResource(id = R.dimen.padding_large))
                        .graphicsLayer(scaleX = -1f),
                    painter = painterResource(id = R.drawable.baseline_logout_48),
                    contentDescription = "send message"
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
                state = listStateOld,
                horizontalAlignment = Alignment.Start
            ) {
//                itemsIndexed(messagesListViewModelOld) { index, item ->
//                    Card(
//                        modifier = Modifier
//                            .padding(
//                                vertical = dimensionResource(id = R.dimen.padding_xsmall),
//                                horizontal = dimensionResource(id = R.dimen.padding_small)
//                            ),
//                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_medium)),
//                        colors = CardDefaults.cardColors(Color.White),
//                        border = BorderStroke(dimensionResource(id = R.dimen.thickness_divider), Grey10),
//                        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.padding_xsmall))
//                    ) {
//                        Text(
//                            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_s_m)),
//                            text = item
//                        )
//                    }
//                }
                itemsIndexed(messagesList) { index, item ->
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
                        Row() {
                            Text(
                                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_s_m)),
                                text = item.sender
                            )
                            Text(
                                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_s_m)),
                                text = item.content
                            )
                            Text(
                                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_s_m)),
                                text = SimpleDateFormat("HH:mm").format(item.timestamp)
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Grey10)
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_xsmall),
                        bottom = dimensionResource(id = R.dimen.padding_small)
                    ),
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
//                    label = { Text(text = stringResource(id = R.string.message)) },
                    placeholder = {
                        if (inputText.isEmpty()) Text(text = stringResource(id = R.string.message))
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    ),
                    shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_large)),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White
                    ),
                    singleLine = false,
                    maxLines = 5
                )
                IconButton(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.padding_large))
                        .clip(CircleShape)
                        .background(Purple40),
                    onClick = {
//                        viewModelMain.sendMessageOld(inputText)

                        viewModelMain.sendMessage(currentUser, inputText)

                        inputText = ""
                    },
                    enabled = inputText.isNotEmpty()
                ) {
                    Icon(
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.padding_large)),
                        painter = painterResource(id = R.drawable.baseline_arrow_upward_48),
                        contentDescription = "send message",
                        tint = Color.White,
                    )
                }
            }
        }
    }
}