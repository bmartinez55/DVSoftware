import el.dv.buildSrc.Dependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "el.dv.domain"
    compileSdk = Dependencies.compileSdk

    defaultConfig {
        minSdk = Dependencies.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        javaCompileOptions {
            annotationProcessorOptions {
                mapOf("room.schemaLocation" to "$projectDir/schemas", "room.incremental" to "true")
            }
        }
    }

    buildTypes {
        release {}
        debug {
            enableUnitTestCoverage = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    api(Dependencies.AndroidX.coreKtx)
    api(Dependencies.AndroidX.appCompat)

    // Threads
    api(Dependencies.Jetbrains.coroutinesCore)
    api(Dependencies.Jetbrains.coroutinesAndroid)

    // DI
    api(Dependencies.DI.koinCore)
    api(Dependencies.DI.koinAndroid)

    // Room
    api(Dependencies.Room.runtime)
    kapt(Dependencies.Room.compiler)
    annotationProcessor(Dependencies.Room.compiler)

    // Test
    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.junitTest)
    androidTestImplementation(Dependencies.Test.espresso)
}
