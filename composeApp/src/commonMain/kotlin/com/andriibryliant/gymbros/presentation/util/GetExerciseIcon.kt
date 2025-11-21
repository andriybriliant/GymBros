package com.andriibryliant.gymbros.presentation.util

import com.andriibryliant.gymbros.domain.model.StoredIconResName
import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.DrawableResource

abstract class GetIconResource {
    companion object{
        fun get(iconResName: String): DrawableResource{
            return try {
                StoredIconResName.asResource(iconResName)
            }catch (e: Exception){
                Res.drawable.compose_multiplatform
            }
        }
    }
}