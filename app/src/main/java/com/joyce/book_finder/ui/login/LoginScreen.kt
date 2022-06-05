package com.joyce.book_finder.ui.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.joyce.book_finder.R
import com.joyce.book_finder.customViews.TextFieldCustom
import com.joyce.book_finder.ui.splash.ui.theme.PRIMARY
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(navController: NavHostController) {
    val viewModel = getViewModel<LoginViewModel>()
    val localFocusManager = LocalFocusManager.current
    var passwordVisible by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()
    when(state){
        is LoginState.Success -> {
            navController.navigate("books")
        }
        is LoginState.Error -> {
            Toast.makeText(LocalContext.current, (state as LoginState.Error).message, Toast.LENGTH_LONG).show() }
        else -> {}
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "@null"
        )
        Spacer(modifier = Modifier.height(26.dp))
        TextFieldCustom(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp),
            labelText = "Email",
            inputWrapper = viewModel.emailInput,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            onImeKeyAction = { localFocusManager.moveFocus(FocusDirection.Down) },
        )
        Spacer(modifier = Modifier.height(26.dp))
        TextFieldCustom(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp),
            isPassword = true,
            labelText = "Senha",
            inputWrapper = viewModel.passwordInput,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image =
                    if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Show password" else "Hide password"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = image,
                        description,
                        tint = if (!viewModel.passwordInput.inputError.hasError) Color.Black else MaterialTheme.colors.error
                    )
                }
            },
            onImeKeyAction = {
                viewModel.passwordInput.onValueChange(viewModel.passwordInput.value)
                localFocusManager.clearFocus()
            }
        )
        Spacer(modifier = Modifier.height(26.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            onClick = { viewModel.login() }) {
            Text(text = "Entrar")
        }
        Spacer(modifier = Modifier.height(10.dp))
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            val (text1, text2) = createRefs()
            Text(
                fontSize = 18.sp,
                modifier = Modifier
                    .constrainAs(text1) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                color = MaterialTheme.colors.error,
                text = "Esqueci minha senha"
            )
            Text(
                text = "Cadastre-se",
                color = PRIMARY,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .constrainAs(text2) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}