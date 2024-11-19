package com.stellerbyte.uptodo.screens.authentication.deleteAccount

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stellerbyte.uptodo.R
import com.stellerbyte.uptodo.components.DrawableIcon
import com.stellerbyte.uptodo.components.InputField
import com.stellerbyte.uptodo.navigation.BottomBar
import com.stellerbyte.uptodo.ui.theme.Purple40

@Composable
fun DeleteUserScreen(
    navigate: (String) -> Unit,
    viewModel: DeleteAccountViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.value

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    )
    {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(top = 20.dp, start = 10.dp)
        ) {
            DrawableIcon(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "ArrowBack",
                tint = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.clickable { navigate(BottomBar.Profile.route) }
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 80.dp)
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(id = R.string.delete_label),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 30.dp)
            )

            Text(
                text = stringResource(id = R.string.delete_description),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 30.dp, top = 10.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            InputField(
                placeholderText = stringResource(id = R.string.emailPlaceholder),
                onFieldChange = viewModel::onEmailChange,
                value = uiState.email,
                modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                label = stringResource(id = R.string.labelEmail)
            )

            Spacer(modifier = Modifier.height(20.dp))
            InputField(
                value = uiState.password,
                placeholderText = stringResource(id = R.string.passPlaceholder),
                onFieldChange = viewModel::onPasswordChange,
                modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                label = stringResource(id = R.string.labelPassword),
                isFieldSecured = true
            )

            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = { viewModel.onDeleteClick(navigate, context) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .padding(start = 30.dp, end = 30.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(text = stringResource(id = R.string.delete_account))
            }
        }
    }
}