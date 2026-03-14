package com.devbilal.zat

import com.devbilal.domain.usecase.settings.VersionManager
import org.koin.core.scope.Scope

class IosVersionManager() : VersionManager {
    override fun getVersionName(): String {
        TODO("Not yet implemented")
    }

    override fun getVersionCode(): Int {
        TODO("Not yet implemented")
    }

    override fun getFullVersion(): String {
        TODO("Not yet implemented")
    }

}

actual fun Scope.createVersionManager(): VersionManager = IosVersionManager()