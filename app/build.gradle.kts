plugins {
    alias(libs.plugins.android.application)
}

android {
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

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.room.common.jvm)
    implementation(libs.sqlite.android)
    implementation("androidx.room:room-runtime:2.5.2")
    implementation(libs.room.runtime.android)
    annotationProcessor("androidx.room:room-compiler:2.5.2")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Room Libraries

    implementation("android.arch.persistence.room:runtime:1.1.1")
    annotationProcessor("android.arch.persistence.room:compiler:1.1.1")

    //Live Data Libraries

    implementation("android.arch.lifecycle:livedata:1.1.1")

    //View Model Libraries
     implementation("android.arch.lifecycle:viewmodel:1.1.1")
    implementation("android.arch.lifecycle:extensions:1.1.1")
    annotationProcessor("android.arch.lifecycle:compiler:1.1.1")

}