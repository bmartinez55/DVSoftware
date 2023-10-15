package el.dv.buildSrc

object Dependencies {
    const val compileSdk = 33
    const val targetSdk = 33
    const val minSdk = 30

    object Jetbrains {
        const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val dataBindingCompiler = "androidx.databinding:databinding-compiler:${Versions.databindingCompiler}"
    }

    object Firebase {
        const val authKtx = "com.google.firebase:firebase-auth-ktx"
    }

    object Google {
        const val playServicesAuth = "com.google.android.gms:play-services-auth:${Versions.playServicesAuth}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val junitTest = "androidx.test.ext:junit:${Versions.junitTest}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }
}
