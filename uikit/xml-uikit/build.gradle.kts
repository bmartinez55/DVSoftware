import el.dv.buildSrc.Dependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "el.dv.xml_uikit"
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    sourceSets.getByName("main") {
        res {
            srcDirs("src/main/res")
        }
    }
}

dependencies {
    api(Dependencies.AndroidX.coreKtx)
    api(Dependencies.AndroidX.appCompat)
    api(Dependencies.AndroidX.constraintLayout)

    api(Dependencies.Google.material)
    api(Dependencies.Google.playServicesMap)
    api(Dependencies.Google.mapsUtils)
    api(Dependencies.Google.mapsKtx)
    api(Dependencies.Google.mapsUtilsKtx)
}
