package com.andriibryliant.gymbros.domain.model

import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.DrawableResource

enum class StoredIconResName(
    val resource: DrawableResource,
    val storedName: String
) {
    ComposeMultiplatform(Res.drawable.compose_multiplatform, "compose");

    companion object{
        fun asResource(storedName: String): DrawableResource = entries.first{ it.storedName == storedName }.resource
        fun asString(resource: DrawableResource): String = entries.first{ it.resource == resource }.storedName
    }
}