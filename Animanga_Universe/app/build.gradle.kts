
plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.animanga_universe"
    compileSdk = 34



    defaultConfig {
        applicationId = "com.example.animanga_universe"
        minSdk = 33
        //noinspection OldTargetApi
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures{
        viewBinding = true
    }
}
dependencies {
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation(files("libs/activation.jar"))
    implementation(files("libs/additional.jar"))
    implementation(files("libs/mail.jar"))
    implementation("com.google.firebase:firebase-core:21.1.1")
    implementation("com.google.firebase:firebase-firestore:25.0.0")
    implementation(files("libs/core-12.1.0-javadoc.jar"))
    implementation("com.google.firebase:firebase-config:22.0.0")
    implementation("com.google.firebase:firebase-storage:21.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
    implementation ("com.google.firebase:firebase-ml-natural-language:22.0.1")
    implementation("com.google.mlkit:translate:17.0.2")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.17")
}
