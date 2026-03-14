package com.devbilal.zat

import android.content.Context
import org.koin.core.scope.Scope
import android.content.pm.PackageManager
import android.os.Build
import com.devbilal.domain.usecase.settings.VersionManager

class AndroidVersionManager(val context: Context): VersionManager {
    override fun getVersionName(): String {
        return try {
            val packageInfo = getPackageInfo(context)
            packageInfo.versionName ?: "1.0.0"
        } catch (e: Exception) {
            BuildConfig.VERSION_NAME
        }
    }

    override fun getVersionCode(): Int {
        return try {
            val packageInfo = getPackageInfo(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode.toInt()
            } else {
                @Suppress("DEPRECATION")
                packageInfo.versionCode
            }
        } catch (e: Exception) {
            BuildConfig.VERSION_CODE
        }
    }

    override fun getFullVersion(): String {
        return "v${getVersionName()} (${getVersionCode()})"
    }

    private fun getPackageInfo(context: Context): android.content.pm.PackageInfo {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.PackageInfoFlags.of(0)
            )
        } else {
            @Suppress("DEPRECATION")
            context.packageManager.getPackageInfo(context.packageName, 0)
        }
    }
}

actual fun Scope.createVersionManager(): VersionManager = AndroidVersionManager(get())