package com.andriibryliant.gymbros.domain.model

import gymbros.composeapp.generated.resources.Res
import gymbros.composeapp.generated.resources.ic_incline_bench_press
import gymbros.composeapp.generated.resources.ic_lat_pulldown
import gymbros.composeapp.generated.resources.ic_leg_press
import gymbros.composeapp.generated.resources.ic_overhead_dumbbell_press
import gymbros.composeapp.generated.resources.ic_seated_arm_curl
import gymbros.composeapp.generated.resources.ic_seated_arm_curl_front
import org.jetbrains.compose.resources.DrawableResource

enum class StoredIconResName(
    val resource: DrawableResource,
    val storedName: String
) {
    LatPulldown(Res.drawable.ic_lat_pulldown, "lat_pulldown"),
    LegPress(Res.drawable.ic_leg_press, "leg_press"),
    InclineBenchPress(Res.drawable.ic_incline_bench_press, "incline_bench_press"),
    SeatedArmCurl(Res.drawable.ic_seated_arm_curl, "seated_arm_curl"),
    SeatedArmCurlFront(Res.drawable.ic_seated_arm_curl_front, "seated_arm_curl_front"),
    OverheadDumbbellPress(Res.drawable.ic_overhead_dumbbell_press, "overhead_dumbbell_press");

    companion object{
        fun asResource(storedName: String): DrawableResource = entries.first{ it.storedName == storedName }.resource
        fun asString(resource: DrawableResource): String = entries.first{ it.resource == resource }.storedName
    }
}