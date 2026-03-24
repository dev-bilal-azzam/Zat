package com.devbilal.domain.entity

/**
 * A pure Kotlin representation of color using ARGB Long value.
 */
data class DiaryColor(val value: Long) {
    companion object {
        val Default = DiaryColor(0xFFFFFFFF) // White
    }
}
