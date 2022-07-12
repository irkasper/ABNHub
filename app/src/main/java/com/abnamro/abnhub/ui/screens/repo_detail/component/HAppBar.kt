package com.abnamro.abnhub.ui.screens.repo_detail.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.abnamro.abnhub.R

@Composable
fun HAppBar(
    title: String,
    backClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.h6) },
        backgroundColor = if (MaterialTheme.colors.isLight) MaterialTheme.colors.primary
        else MaterialTheme.colors.primaryVariant,
        navigationIcon =
        {
            IconButton(onClick = { backClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.cd_back_to_home_screen)
                )
            }
        }
    )
}
