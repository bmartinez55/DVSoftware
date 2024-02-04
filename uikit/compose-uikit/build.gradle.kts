import el.dv.buildSrc.Dependencies
import el.dv.buildSrc.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "el.dv.compose_uikit"
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
    buildFeatures {
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
    sourceSets.getByName("main").java.srcDirs("src/main/res").includes.addAll(arrayOf("**/*.*"))
}

dependencies {
    api(Dependencies.AndroidX.composeAnimation)
    api(Dependencies.AndroidX.composeFoundation)
    api(Dependencies.AndroidX.composeMaterial)
    api(Dependencies.AndroidX.composeRuntime)
    api(Dependencies.AndroidX.composeUi)
    api(Dependencies.AndroidX.composeRuntimeLiveData)
    api(Dependencies.AndroidX.composeConstraintLayout)
    api(Dependencies.AndroidX.composeUiTooling)
    api(Dependencies.AndroidX.composeUiToolingPreview)
    api(Dependencies.AndroidX.composeNavigation)
    api(Dependencies.AndroidX.composeLifecycleViewModel)
    api(Dependencies.AndroidX.composeMaterial3)

    api(Dependencies.Google.accompanistInsets)
    api(Dependencies.Google.accompanistFlowLayout)

    api(Dependencies.ThirdParty.coilCompose)
}
