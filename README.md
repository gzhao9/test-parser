# Test Case Parser

A Java-based tool for parsing and analyzing test cases from Java projects. This tool scans Java source files, extracts test methods with their annotations, and exports the results to JSON format.

## How to Use the JAR

### Prerequisites
- Java 11 or higher

### Running the Fat JAR
The fat JAR includes all dependencies and can be run directly:

```bash
# Search current directory, output to ./testcases.json
java -jar target/test-parser-1.0-SNAPSHOT-jar-with-dependencies.jar

# Search specific directory, output to ./testcases.json
java -jar target/test-parser-1.0-SNAPSHOT-jar-with-dependencies.jar "C:\MyProject"

# Custom search path and output file
java -jar target/test-parser-1.0-SNAPSHOT-jar-with-dependencies.jar "C:\MyProject" "C:\Output\results.json"
```

### Command Line Arguments
| Arguments | Description | Example |
|-----------|-------------|---------|
| None | Search current directory, output to `./testcases.json` | `java -jar app.jar` |
| `<search_path>` | Search specified path, output to `./testcases.json` | `java -jar app.jar "C:\MyProject"` |
| `<search_path> <output_json>` | Search specified path, output to specified file | `java -jar app.jar "C:\MyProject" "results.json"` |

## How to Build the Project

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Steps to Build
1. Clone or download the source code.
2. Navigate to the project directory.
3. Run the following command to build the project:

```bash
mvn clean package
```

This will generate two JAR files in the `target/` directory:
- `test-parser-1.0-SNAPSHOT.jar` - Thin jar (requires dependencies in classpath)
- `test-parser-1.0-SNAPSHOT-jar-with-dependencies.jar` - Fat jar (standalone)

### Running the Thin JAR
If using the thin JAR, you need to specify the classpath:

```bash
java -cp "target/test-parser-1.0-SNAPSHOT.jar;target/dependency/*" com.testparser.TestParserApp [arguments]
```