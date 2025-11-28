package com.andriibryliant.gymbros.presentation.settings

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.andriibryliant.gymbros.domain.AppThemeMode
import com.andriibryliant.gymbros.domain.localization.Language
import com.andriibryliant.gymbros.presentation.main.components.DefaultTopBar
import com.andriibryliant.gymbros.presentation.settings.components.LanguageBottomSheet
import com.andriibryliant.gymbros.presentation.settings.components.ThemeToggleIcon
import com.andriibryliant.gymbros.presentation.theme.isDynamicColorAvailable
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.dark
import gymbros.composeapp.generated.resources.dynamic_color
import gymbros.composeapp.generated.resources.dynamic_color_label
import gymbros.composeapp.generated.resources.language
import gymbros.composeapp.generated.resources.light
import gymbros.composeapp.generated.resources.settings
import gymbros.composeapp.generated.resources.system
import gymbros.composeapp.generated.resources.theme_mode
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SettingsScreen(
    language: Language,
    theme: AppThemeMode,
    isDynamicColor: Boolean,
    onBackClick: () -> Unit,
    onLanguageChange: (String) -> Unit,
    onAppThemeChange: (AppThemeMode) -> Unit,
    onDynamicColorChange: (Boolean) -> Unit
){
    var showLanguageSheet by remember { mutableStateOf(false) }
    var showModeDropDown by remember { mutableStateOf(false) }

    key(language){
        Scaffold(
            topBar = {
                DefaultTopBar(
                    title = stringResource(Res.string.settings),
                    onBackClick = onBackClick
                )
            },
            modifier = Modifier
                .fillMaxSize()
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{
                            showLanguageSheet = true
                        }
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            Icons.Default.Language,
                            null,
                            modifier = Modifier
                                .size(32.dp)
                        )
                        Spacer(Modifier.size(8.dp))
                        Text(
                            text = stringResource(Res.string.language),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .animateContentSize()
                        )
                    }
                    ElevatedFilterChip(
                        selected = true,
                        onClick = {
                            showLanguageSheet = true
                        },
                        label = {
                            Text(
                                stringResource(language.resource)
                            )
                        },
                        modifier = Modifier
                            .animateContentSize()
                    )
                }


                if(isDynamicColorAvailable()){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable{
                                onDynamicColorChange(!isDynamicColor)
                            }
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Icon(
                                Icons.Default.Palette,
                                null,
                                modifier = Modifier
                                    .size(32.dp)
                            )
                            Spacer(Modifier.size(8.dp))
                            Column(
                                modifier = Modifier
                                    .widthIn(max = 192.dp)
                            ){
                                Text(
                                    text = stringResource(Res.string.dynamic_color),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = stringResource(Res.string.dynamic_color_label),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                        Switch(
                            checked = isDynamicColor,
                            onCheckedChange = {
                                onDynamicColorChange(it)
                            }
                        )
                    }
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{
                            showModeDropDown = !showModeDropDown
                        }
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        ThemeToggleIcon(
                            isDark = theme
                        )
                        Spacer(Modifier.size(8.dp))
                            Text(
                                text = stringResource(Res.string.theme_mode),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                    }
                    Box{
                        ElevatedFilterChip(
                            selected = true,
                            onClick = {
                                showModeDropDown = !showModeDropDown
                            },
                            label = {
                                Text(
                                    stringResource(
                                        when(theme){
                                            AppThemeMode.LIGHT -> Res.string.light
                                            AppThemeMode.DARK -> Res.string.dark
                                            AppThemeMode.SYSTEM -> Res.string.system
                                        }
                                    )
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    null
                                )
                            },
                            modifier = Modifier.animateContentSize()
                        )
                        DropdownMenu(
                            expanded = showModeDropDown,
                            onDismissRequest = {
                                showModeDropDown = false
                            }
                        ){
                            DropdownMenuItem(
                                text = {
                                    Text(stringResource(Res.string.system))
                                },
                                onClick = {
                                    showModeDropDown = false
                                    onAppThemeChange(AppThemeMode.SYSTEM)
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(stringResource(Res.string.dark))
                                },
                                onClick = {
                                    showModeDropDown = false
                                    onAppThemeChange(AppThemeMode.DARK)
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(stringResource(Res.string.light))
                                },
                                onClick = {
                                    showModeDropDown = false
                                    onAppThemeChange(AppThemeMode.LIGHT)
                                }
                            )
                        }
                    }
                }
            }
        }
        if(showLanguageSheet){
            LanguageBottomSheet(
                onDismiss = {
                    showLanguageSheet = false
                },
                language = language,
                onLanguageChoose = { language ->
                    onLanguageChange(language.iso)
                    showLanguageSheet = false
                }
            )
        }
    }
}