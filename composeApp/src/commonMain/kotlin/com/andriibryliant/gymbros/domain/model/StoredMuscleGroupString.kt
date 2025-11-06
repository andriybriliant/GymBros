package com.andriibryliant.gymbros.domain.model

import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.abs_group
import gymbros.composeapp.generated.resources.back_group
import gymbros.composeapp.generated.resources.biceps_group
import gymbros.composeapp.generated.resources.chest_group
import gymbros.composeapp.generated.resources.forearms_group
import gymbros.composeapp.generated.resources.legs_group
import gymbros.composeapp.generated.resources.triceps_group
import org.jetbrains.compose.resources.StringResource

enum class StoredMuscleGroupString(
    val resource: StringResource,
    val storedName: String
) {
    ChestString(Res.string.chest_group, "chest"),
    BackString(Res.string.back_group, "back"),
    LegsString(Res.string.legs_group, "legs"),
    BicepsString(Res.string.biceps_group, "biceps"),
    TricepsString(Res.string.triceps_group, "triceps"),
    ForearmsString(Res.string.forearms_group, "forearms"),
    AbsString(Res.string.abs_group, "abs");

    companion object{
        fun asStringResource(storedName: String): StringResource = entries.first{ it.storedName == storedName}.resource
    }
}