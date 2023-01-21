apply(plugin = "kotlin-jpa")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // mariadb
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
}