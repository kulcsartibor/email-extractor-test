rootProject.name = "email-detector"


dependencyResolutionManagement {
    versionCatalogs{
        create("libs"){
        }

        create("testLibs"){
            version("junit5", "5.10.2")

            library("junit-jupiter-api","org.junit.jupiter", "junit-jupiter-api").versionRef("junit5")
            library("junit-jupiter-engine","org.junit.jupiter", "junit-jupiter-engine").versionRef("junit5")
        }
    }

}