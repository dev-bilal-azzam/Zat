package com.devbilal.domain.usecase.settings

interface VersionManager {
    fun getVersionName(): String
    fun getVersionCode(): Int
    fun getFullVersion(): String
}

