package com.andriibryliant.gymbros.domain.localization

import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.english
import gymbros.composeapp.generated.resources.polish
import gymbros.composeapp.generated.resources.ukrainian
import org.jetbrains.compose.resources.StringResource

enum class Language(
    val iso: String,
    val resource: StringResource
) {
    English(iso = "en", Res.string.english),
    Polish(iso = "pl", Res.string.polish),
    Ukrainian(iso = "uk", Res.string.ukrainian)
}