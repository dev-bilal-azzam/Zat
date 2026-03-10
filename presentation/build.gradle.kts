
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {

        commonMain {
            dependencies {
                // Project
                implementation(project(":designSystem"))
                implementation(project(":domain"))

                implementation(libs.bundles.compose)
                implementation(libs.bundles.lifecycle)
                implementation(libs.bundles.koin.compose)
                implementation(libs.bundles.navigation3)

                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.serialization.json)
            }

        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.biometric)
            }
        }

        iosMain {
            dependencies {
            }
        }
    }

}

android {
    namespace = "com.devbilal.presentation"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}