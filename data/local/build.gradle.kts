plugins {
    alias(libs.plugins.valorant.android.library)
    alias(libs.plugins.valorant.android.library.jacoco)
    alias(libs.plugins.valorant.android.hilt)
    alias(libs.plugins.valorant.android.room)
}

android {
    namespace = "com.example.valorant.data.local"
}

dependencies {

    implementation(projects.domain)
    implementation(libs.paging.runtime)
    implementation(libs.room.paging)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
}