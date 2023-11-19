### How To Run:

1. Set $JAVA_HOME to Java 11 JDK e.g.
`export JAVA_HOME=/Library/Java/JavaVirtualMachines/openjdk-11.jdk/Contents/Home`

2. Verify tests execute successfully:
`./gradlew test --scan`

3. Run the service:
`./gradlew bootRun --scan`

4. Use the provided Postman collection in `/postman` directory to get you going
