rootProject.name = "DVSoftware"
include(":FayucaFinder")
include(":domain")
include(":presentation")
include(":DVProperties")

include(":xml-uikit")
project(":xml-uikit").projectDir = File(rootProject.projectDir, "uikit/xml-uikit")
include(":compose-uikit")
project(":compose-uikit").projectDir = File(rootProject.projectDir, "uikit/compose-uikit")
include(":DataCommon")
project(":DataCommon").projectDir = File(rootProject.projectDir, "/data/DataCommon")
include(":DVPropertiesData")
project(":DVPropertiesData").projectDir = File(rootProject.projectDir, "/data/DVPropertiesData")
include(":FayucaFinderData")
project(":FayucaFinderData").projectDir = File(rootProject.projectDir, "/data/FayucaFinderData")

include(":weatherapp")
