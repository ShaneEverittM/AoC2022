plugins {
    kotlin("multiplatform") version "1.7.20"
}

group = "space.shanemurphy"
version = "0.1.0"

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }
    sourceSets {
        @Suppress("UNUSED_VARIABLE") val nativeMain by getting {
            dependencies {
                implementation("com.squareup.okio:okio:3.2.0")
            }
        }
        @Suppress("UNUSED_VARIABLE") val nativeTest by getting
    }
}
