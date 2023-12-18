package el.dv.buildSrc

object Dependencies {
    const val compileSdk = 34
    const val targetSdk = 33
    const val minSdk = 30

    object Jetbrains {
        const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val dataBindingCompiler = "androidx.databinding:databinding-compiler:${Versions.databindingCompiler}"
        const val composeAnimation = "androidx.compose.animation:animation:${Versions.compose}"
        const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
        const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.compose}"
        const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
        const val composeRuntimeLiveData = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
        const val composeConstraintLayout = "androidx.constraintlayout:constraintlayout-compose:${Versions.composeConstraintLayout}"
        const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val composeNavigation = "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
        const val composeLifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val composeMaterial3 = "androidx.compose.material3:material3:${Versions.composeMaterial3}"
    }

    object DI {
        const val koinCore = "io.insert-koin:koin-core:${Versions.koin}"
        const val koinAndroid = "io.insert-koin:koin-android:${Versions.koinAndroid}"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:${Versions.room}"
        const val compiler = "androidx.room:room-compiler:${Versions.room}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    }

    object Firebase {
        const val authKtx = "com.google.firebase:firebase-auth-ktx"
        const val bom = "com.google.firebase:firebase-bom:${Versions.bom}"
        const val databaseKtx = "com.google.firebase:firebase-database-ktx"
    }

    object Google {
        const val playServicesAuth = "com.google.android.gms:play-services-auth:${Versions.playServicesAuth}"
        const val mapsUtils = "com.google.maps.android:android-maps-utils:${Versions.mapsUtils}"
        const val accompanistInsets = "com.google.accompanist:accompanist-insets:${Versions.accompanistInsets}"
        const val accompanistFlowLayout = "com.google.accompanist:accompanist-flowlayout:${Versions.accompanistFlowLayout}"
        const val material = "com.google.android.material:material:${Versions.googleMaterial}"
        const val playServicesMap = "com.google.android.gms:play-services-maps:${Versions.playServicesMap}"
        const val mapsKtx = "com.google.maps.android:maps-ktx:${Versions.mapsKtx}"
        const val mapsUtilsKtx = "com.google.maps.android:maps-utils-ktx:${Versions.mapsKtx}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val junitTest = "androidx.test.ext:junit:${Versions.junitTest}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }
}
