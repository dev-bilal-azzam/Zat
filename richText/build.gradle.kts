plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.bundles.compose)
                api(libs.richeditor.compose)
                implementation(libs.compose.material3)
            }
        }

        commonTest {
            dependencies {
            }
        }

        androidMain {
            dependencies {
            }
        }


        iosMain {
            dependencies {
            }
        }
    }

}

android {
    namespace = "com.devbilal.richtext"
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
