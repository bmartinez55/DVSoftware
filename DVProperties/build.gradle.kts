import el.dv.buildSrc.Dependencies
import el.dv.buildSrc.Versions

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "el.dv.dvproperties"
    compileSdk = Dependencies.compileSdk

    defaultConfig {
        applicationId = "el.dv.dvproperties"
        minSdk = Dependencies.minSdk
        targetSdk = Dependencies.targetSdk
        versionCode = 1
        versionName = "1.0"
        compileSdkPreview = "UpsideDownCake"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":presentation"))

    // Core
    implementation(Dependencies.Jetbrains.kotlinStdlib)

    // Androidx
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)
    annotationProcessor(Dependencies.AndroidX.dataBindingCompiler)

    // Google & Firebase
    implementation(Dependencies.Firebase.authKtx)
    implementation(Dependencies.Google.playServicesAuth)

    // Test
    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.junitTest)
    androidTestImplementation(Dependencies.Test.espresso)
}
