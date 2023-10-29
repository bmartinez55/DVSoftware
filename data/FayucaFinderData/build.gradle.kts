import el.dv.buildSrc.Dependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "el.dv.fayucafinderdata"
    compileSdk = Dependencies.compileSdk

    defaultConfig {
        minSdk = Dependencies.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    api(project(":domain"))
    api(project(":DataCommon"))
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)

    implementation(platform(Dependencies.Firebase.bom))
    implementation(Dependencies.Firebase.databaseKtx)

    implementation(Dependencies.DI.koinCore)
    implementation(Dependencies.DI.koinAndroid)

    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.junitTest)
    androidTestImplementation(Dependencies.Test.espresso)
}
