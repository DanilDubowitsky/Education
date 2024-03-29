plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.testeducation.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kotlinOptions {
        jvmTarget = "11"
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}

dependencies {
    implementation(kotlin("reflect"))

    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.core:core-ktx:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // MVI
    implementation("org.orbit-mvi:orbit-core:4.6.1")
    implementation("org.orbit-mvi:orbit-viewmodel:4.6.1")
    implementation("org.orbit-mvi:orbit-compose:4.6.1")

    // Ktx
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    implementation(project(":domain"))
    implementation(project(":presentation:logic"))
    implementation(project(":navigation"))

    //serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
}