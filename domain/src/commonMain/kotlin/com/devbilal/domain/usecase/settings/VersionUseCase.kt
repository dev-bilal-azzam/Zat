package com.devbilal.domain.usecase.settings

class VersionUseCase(private val versionManager: VersionManager) {

    fun getVersionName(): String = versionManager.getVersionName()

    fun getVersionCode() = versionManager.getVersionCode()

    fun getFullVersion() = versionManager.getFullVersion()

}