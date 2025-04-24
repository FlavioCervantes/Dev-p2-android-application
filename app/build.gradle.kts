plugins {
    alias(libs.plugins.android.application)
}

android {
    dependencies {
        // JUnit for unit testing
        testImplementation("junit:junit:4.13.2")

        // Mockito for mocking dependencies
        testImplementation("org.mockito:mockito-core:4.8.0")

        // Android-specific test utilities
        testImplementation("androidx.test.ext:junit:1.1.5")
        testImplementation("androidx.test:core:1.5.0")
    }
    namespace = "com.example.dev_p2_android_application"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.dev_p2_android_application"
        minSdk = 34
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }





    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {
    implementation(libs.recyclerview)
    androidTestImplementation(libs.junit.jupiter)
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.sqlite.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

}

