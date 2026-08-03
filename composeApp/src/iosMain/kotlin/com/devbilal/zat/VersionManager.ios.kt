package com.devbilal.zat

import com.devbilal.domain.usecase.settings.VersionManager
import org.koin.core.scope.Scope
import platform.Foundation.NSBundle

class IosVersionManager : VersionManager {
    override fun getVersionName(): String {
        return NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String ?: "1.0.0"
    }

    override fun getVersionCode(): Int {
        val buildNumberStr = NSBundle.mainBundle.infoDictionary?.get("CFBundleVersion") as? String
        return buildNumberStr?.toIntOrNull() ?: 1
    }

    override fun getFullVersion(): String {
        return "v${getVersionName()} (${getVersionCode()})"
    }
}

actual fun Scope.createVersionManager(): VersionManager = IosVersionManager()