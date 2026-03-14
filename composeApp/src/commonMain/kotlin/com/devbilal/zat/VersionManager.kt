package com.devbilal.zat

import com.devbilal.domain.usecase.settings.VersionManager
import org.koin.core.scope.Scope

expect fun Scope.createVersionManager(): VersionManager

