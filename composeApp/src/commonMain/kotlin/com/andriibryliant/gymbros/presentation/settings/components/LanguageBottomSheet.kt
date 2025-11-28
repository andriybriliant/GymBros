package com.andriibryliant.gymbros.presentation.settings.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import com.andriibryliant.gymbros.domain.localization.Language
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.language
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageBottomSheet(
    onDismiss: () -> Unit,
    language: Language,
    onLanguageChoose: (Language) -> Unit
) {
    val languageOptions = Language.entries.toList()
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(language) }

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        dragHandle = null,
    ){
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        ){
            Text(
                text = stringResource(Res.string.language),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.size(8.dp))
        }
        LazyColumn(
            Modifier
                .padding(16.dp)
                .selectableGroup(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            items(languageOptions) { language ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(
                            color = if(language == selectedOption){
                                MaterialTheme.colorScheme.onSecondary
                            }else{
                                MaterialTheme.colorScheme.surfaceVariant
                            },
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(
                            RoundedCornerShape(8.dp)

                        )
                        .selectable(
                            selected = (language == selectedOption),
                            onClick = {
                                onOptionSelected(language)
                                onLanguageChoose(language)
                            },
                            role = Role.RadioButton
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    RadioButton(
                        selected = (language == selectedOption),
                        onClick = null
                    )
                    Spacer(Modifier.size(4.dp))
                    Text(
                        stringResource(language.resource)
                    )
                }
            }
        }
    }
}