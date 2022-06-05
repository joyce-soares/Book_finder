package com.joyce.book_finder.customViews
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joyce.book_finder.theme.PRIMARY
import com.joyce.book_finder.utils.InputWrapper


@Composable
fun TextFieldCustom(
    isPassword: Boolean = false,
    modifier: Modifier = Modifier,
    labelText: String = "Label",
    inputWrapper: InputWrapper,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    onImeKeyAction: () -> Unit
) {
    val fieldValue = remember {
        mutableStateOf(TextFieldValue(inputWrapper.value, TextRange(inputWrapper.value.length)))
    }

    Column {
        Text(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
            text = labelText,
            fontSize = 16.sp,
            color = if (!inputWrapper.inputError.hasError) PRIMARY else MaterialTheme.colors.error,
            textAlign = TextAlign.Start
        )
        TextField(
            modifier = modifier
                .fillMaxWidth(),
            singleLine = true,
            value = fieldValue.value,
            onValueChange = {
                fieldValue.value = it
                inputWrapper.onValueChange(it.text)
            },
            isError = inputWrapper.inputError.hasError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(onAny = { onImeKeyAction() }),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = PRIMARY,
                unfocusedIndicatorColor = PRIMARY
            ),
            trailingIcon = trailingIcon
        )
        if (inputWrapper.inputError.hasError) {
            inputWrapper.inputError.errorMessage?.let {
                Text(
                    modifier = Modifier
                        .padding(start = 20.dp),
                    text = it,
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

//@Preview(name = "Light Mode", showBackground = true)
//@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun TextFieldCustomPreview() {
//    val localFocusManager = LocalFocusManager.current
//    var passwordVisible by rememberSaveable { mutableStateOf(false) }
//
//    val passwordInputWrapper =
//        InputWrapper(inputError = InputError(hasError = true, errorMessage = "Required field!")) {}
//    val emailInputWrapper = InputWrapper(
//        value = "preview@email.com",
//        inputError = InputError(hasError = false, errorMessage = null)
//    ) {}
//        Surface {
//            Column {
//                TextFieldCustom(
//                    labelText = "Email",
//                    inputWrapper = emailInputWrapper,
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Email,
//                        imeAction = ImeAction.Next
//                    ),
//                    onImeKeyAction = { localFocusManager.moveFocus(FocusDirection.Down) }
//                )
//
//                Spacer(modifier = Modifier.height(26.dp))
//
//                TextFieldCustom(
//                    labelText = "Password",
//                    inputWrapper = passwordInputWrapper,
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Password,
//                        imeAction = ImeAction.Done
//                    ),
//                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//                    trailingIcon = {
//                        val image =
//                            if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
//                        val description = if (passwordVisible) "Show password" else "Hide password"
//                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
//                            Icon(
//                                imageVector = image,
//                                description,
//                                tint = if (!passwordInputWrapper.inputError.hasError) PRIMARY else MaterialTheme.colors.error
//                            )
//                        }
//                    },
//                    onImeKeyAction = { localFocusManager.clearFocus() }
//                )
//            }
//        }
//}