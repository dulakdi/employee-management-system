# Employee Management System

Simple Java-based Employee Management System (desktop UI).  
This repository contains Java source and compiled class files, including a Swing UI (EmployeeUI.java), the program entry point (Main.java), and a DatabaseManager class that handles persistence.

---

## Contents

- `Main.java` — program entry point
- `EmployeeUI.java` — Swing UI for managing employees
- `DatabaseManager.java` — database access and setup code (check this file for connection details)
- `Employee.java`, `EmployeeRecord.java`, `Manager.java`, `Executive.java` — domain classes
- `lib/` — place JDBC drivers or other external jars here

---

## Requirements

- Java JDK 8 or newer (JDK 11+ recommended)
- javac/java on PATH
- If the app uses a JDBC database (see `DatabaseManager.java`):
  - Add the appropriate JDBC driver jar (for example `sqlite-jdbc.jar` or a MySQL/Postgres JDBC driver) to the `lib/` folder and classpath.
- (Optional) An IDE such as IntelliJ IDEA, Eclipse or VS Code + Java extensions

---

## Quick start — build and run from source

1. Clone the repository (if you haven't already):

   git clone https://github.com/dulakdi/employee-management-system.git
   cd employee-management-system

2. Put any required JDBC driver(s) into the `lib/` directory. If the project uses SQLite, add a `sqlite-jdbc-<version>.jar`. If it uses MySQL/Postgres, add those drivers accordingly.

3. Compile all Java files:

   On macOS / Linux:
   javac -cp "lib/*:." *.java

   On Windows (PowerShell / cmd):
   javac -cp "lib/*;." *.java

   This produces .class files next to the source files (or uses the precompiled class files in the repo).

4. Run the application (Main is the entry point):

   On macOS / Linux:
   java -cp "lib/*:.:." Main

   On Windows:
   java -cp "lib/*;.;." Main

   Note: If you packaged a jar (see packaging below), you can run the jar directly.

---

## Packaging into an executable JAR

1. After compiling, create a runnable jar with Main as the main class:

   jar cfe EmployeeManagement.jar Main *.class

2. Run the jar including any external jars in `lib/`:

   On macOS / Linux:
   java -cp "EmployeeManagement.jar:lib/*" Main

   Or (if you put the Main class as the jar entrypoint):
   java -jar EmployeeManagement.jar

   On Windows:
   java -cp "EmployeeManagement.jar;lib/*" Main

If using `java -jar` and external libraries, you must either:
- Build a "fat" (uber) jar that includes dependencies, or
- Use a launcher script that sets the classpath to include `lib/*`.

---

## Database setup and notes

- The repository contains `DatabaseManager.java`. Open that file and check the connection string (for example `jdbc:sqlite:employees.db` or a MySQL/Postgres URL).
- If the app uses an embedded DB (like SQLite), the DB file is often created automatically in the project directory. If it expects an external DB, create the database and user as described in `DatabaseManager.java`.
- Make sure the JDBC driver is present in `lib/` and on the classpath when running.
- Example: if `DatabaseManager` uses `jdbc:sqlite:employees.db`, you can use `sqlite-jdbc` in `lib/` and no external DB server is needed.

---

## Typical workflow / UI usage

- Launch the app (see Quick start).
- The Swing UI (EmployeeUI) lets you:
  - Add a new employee (name, role, salary, etc.)
  - Edit employee details
  - Delete employee records
  - Search or list employees
- The UI stores/retrieves records via `DatabaseManager`.

Because implementations vary, check `EmployeeUI.java` and `DatabaseManager.java` for exact fields and operation labels.

---

## Troubleshooting

- ClassNotFoundException for JDBC driver:
  - Ensure the correct driver jar is in `lib/` and added to the classpath when compiling/running.
- Compilation errors:
  - Use a compatible JDK version (the code appears to be standard Java; JDK 8+ should work).
  - Ensure `lib/*` classpath contains any external jars referenced in imports.
- GUI doesn't open or immediately exits:
  - Run `java Main` from a terminal to see console errors and stack traces.

---

## Development tips

- Use an IDE: import the folder as a Java project and add `lib/*` to project libraries.
- If you plan to recompile often, consider adding a simple Gradle or Maven build file to manage dependencies and build artifacts.
- To create a "fat jar" automatically, use Maven Shade Plugin or the Gradle Shadow plugin.

---

## Contributing

1. Fork the repo
2. Create a branch: `git checkout -b feature/your-feature`
3. Make changes, test locally
4. Open a pull request describing your changes

---

## License

Add your preferred license file (e.g., MIT, Apache 2.0) to the repository. If you want, I can add a license file for you.

---
