plugins {
	java
}

group = "com.exm"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	testImplementation(testLibs.junit.jupiter.api)
	testImplementation(testLibs.junit.jupiter.engine)
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<Copy>("copyTestReport") {
	dependsOn("test")

	from(layout.buildDirectory.dir("reports/tests"))
	into(layout.projectDirectory.dir("archiveReports/tests"))
}
