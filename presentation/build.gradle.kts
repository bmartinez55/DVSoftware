import el.dv.buildSrc.Dependencies
import el.dv.buildSrc.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.gms.google-services")
}

android {
    namespace = "el.dv.presentation"
    compileSdk = Dependencies.compileSdk

    defaultConfig {
        minSdk = Dependencies.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            enableUnitTestCoverage = true
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
        animationsDisabled = true
    }
    sourceSets.getByName("main") {
        this.res {
            srcDirs("src/main/res", "src/main/res/values")
        }
    }
}

dependencies {
    api(project(":DataCommon"))
    api(project(":xml-uikit"))
    api(project(":compose-uikit"))

    // Androidx
    api(Dependencies.AndroidX.coreKtx)
    api(Dependencies.AndroidX.appCompat)
    api(Dependencies.AndroidX.lifeCycleLiveDataKtx)
    api(Dependencies.AndroidX.lifeCycleViewModelKtx)
    api(Dependencies.AndroidX.lifeCycleRuntimeKtx)
    api(Dependencies.AndroidX.lifeCycleCommonJava8)
    api(Dependencies.AndroidX.preferencesKtx)
    api(Dependencies.AndroidX.legacySupportV4)
    api(Dependencies.AndroidX.supportAppCompatV7)

    // Jetpack Suite or UI
    api(Dependencies.AndroidX.fragmentKtx)
    api(Dependencies.Google.material)
    api(Dependencies.AndroidX.constraintLayout)

    // Navigation
    api(Dependencies.AndroidX.navigationFragmentKtx)
    api(Dependencies.AndroidX.navigationUiKtx)

    // Google Firebase && Play Services
    api(Dependencies.Google.playServicesMap)
    api(Dependencies.Google.playServicesLocation)
    api(Dependencies.Google.places) {
        exclude(group = "com.google.code.gson", module = "gson")
    }
    api(platform(Dependencies.Firebase.bom))

    // CameraX
    api(Dependencies.AndroidX.camera2)
    api(Dependencies.AndroidX.cameraCore)
    api(Dependencies.AndroidX.cameraExtensions)
    api(Dependencies.AndroidX.cameraLifecycle)
    api(Dependencies.AndroidX.cameraView)
    api(Dependencies.AndroidX.cameraVideo)

    // DI
    api(Dependencies.DI.koinCore)
    api(Dependencies.DI.koinAndroid)

    // Test
    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.junitTest)
    androidTestImplementation(Dependencies.Test.espresso)
}
