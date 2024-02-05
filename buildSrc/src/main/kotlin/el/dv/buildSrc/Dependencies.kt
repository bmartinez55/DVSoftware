package el.dv.buildSrc

object Dependencies {
    const val compileSdk = 34
    const val targetSdk = 33
    const val minSdk = 30

    object Jetbrains {
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
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
        const val lifeCycleLiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        const val lifeCycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val lifeCycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        const val lifeCycleCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
        const val preferencesKtx = "androidx.preference:preference-ktx:${Versions.preferenceKtx}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val composeMaterial3 = "androidx.compose.material3:material3:${Versions.composeMaterial3}"
        const val camera2 = "androidx.camera:camera-camera2:${Versions.cameraX}"
        const val cameraCore = "androidx.camera:camera-core:${Versions.cameraX}"
        const val cameraExtensions = "androidx.camera:camera-extensions:${Versions.cameraX}"
        const val cameraLifecycle = "androidx.camera:camera-lifecycle:${Versions.cameraX}"
        const val cameraView = "androidx.camera:camera-view:${Versions.cameraX}"
        const val cameraVideo = "androidx.camera:camera-video:${Versions.cameraX}"
        const val legacySupportV4 = "androidx.legacy:legacy-support-v4:${Versions.legacySupportV4}"
        const val supportAppCompatV7 = "com.android.support:appcompat-v7:${Versions.supportAppCompatV7}"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
        const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigationKtx}"
        const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigationKtx}"
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
        const val playServicesLocation = "com.google.android.gms:play-services-location:${Versions.playServicesLocation}"
        const val places = "com.google.android.libraries.places:places:${Versions.places}"
    }

    object ThirdParty {
        const val coilCompose = "io.coil-kt:coil-compose:${Versions.coilCompose}"
        const val gson = "com.google.code.gson:gson:${Versions.gson}"
        const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofitGsonConverter}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val junitTest = "androidx.test.ext:junit:${Versions.junitTest}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }
}
