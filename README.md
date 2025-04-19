# Notes CLI - Spring Boot Application

A command-line interface (CLI) application for managing notes, built with Spring Boot. This application allows you to create, read, update, and delete notes through both a REST API and a CLI interface.

## Features

- Create, read, update, and delete notes
- Dual interface: REST API and CLI
- Persistent storage using H2 database
- Simple and intuitive command-line interface
- Spring Shell integration for interactive CLI

## Prerequisites

- Java 17 or higher
- Gradle 8.x
- curl (for the shell script)
- jq (optional, for pretty-printing JSON output)

## Getting Started

### Building the Application

```bash
cd notes-cli
./gradlew build
```

### Running the Application

There are two ways to run the application:

#### 1. Using Spring Shell (Interactive CLI)

```bash
cd notes-cli
./gradlew bootRun
```

Once the application starts, you'll see a `shell:>` prompt where you can enter commands:

```
shell:> list
shell:> create "My Note" "This is the content"
shell:> get 1
shell:> update 1 "Updated Title" "Updated content"
shell:> delete 1
shell:> help
shell:> quit
```

#### 2. Using the Shell Script (Simplified CLI)

For a more user-friendly experience, use the provided shell script:

```bash
cd notes-cli
./notes.sh list
./notes.sh create "My Note" "This is the content"
./notes.sh get 1
./notes.sh update 1 "Updated Title" "Updated content"
./notes.sh delete 1
./notes.sh help
```

The shell script will automatically start the application if it's not already running.

### Using the REST API

The application also exposes a REST API that you can use directly:

- `GET /api/notes` - List all notes
- `GET /api/notes/{id}` - Get a note by ID
- `POST /api/notes` - Create a new note
- `PUT /api/notes/{id}` - Update an existing note
- `DELETE /api/notes/{id}` - Delete a note

Example using curl:

```bash
# List all notes
curl http://localhost:8080/api/notes

# Create a new note
curl -X POST http://localhost:8080/api/notes \
  -H "Content-Type: application/json" \
  -d '{"title":"My Note","content":"This is the content"}'

# Get a note by ID
curl http://localhost:8080/api/notes/1

# Update a note
curl -X PUT http://localhost:8080/api/notes/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated Title","content":"Updated content"}'

# Delete a note
curl -X DELETE http://localhost:8080/api/notes/1
```

## Project Structure

```
notes-cli/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── notes/
│   │   │               └── cli/
│   │   │                   ├── commands/
│   │   │                   │   └── NoteCommands.java
│   │   │                   ├── controller/
│   │   │                   │   └── NoteController.java
│   │   │                   ├── model/
│   │   │                   │   └── Note.java
│   │   │                   ├── repository/
│   │   │                   │   └── NoteRepository.java
│   │   │                   ├── service/
│   │   │                   │   └── NoteService.java
│   │   │                   └── NotesCliApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── notes/
│                       └── cli/
│                           └── NotesCliApplicationTests.java
├── build.gradle
├── notes.sh
└── settings.gradle
```

## Architecture

The application follows a standard Spring Boot architecture:

- **Model**: `Note.java` - Entity class representing a note
- **Repository**: `NoteRepository.java` - JPA repository for database operations
- **Service**: `NoteService.java` - Business logic layer with CRUD operations
- **Controller**: `NoteController.java` - REST API endpoints
- **Commands**: `NoteCommands.java` - Spring Shell commands for CLI operations

## Database

The application uses an H2 database for storing notes. The database file is located at `./data/notesdb` relative to the application's working directory.

## Configuration

The application configuration is defined in `src/main/resources/application.properties`:

- Database configuration
- JPA configuration
- Server configuration
- Spring Shell configuration

## Development

### Adding New Features

To add new features to the application:

1. Update the model if needed
2. Add methods to the service layer
3. Add endpoints to the controller
4. Add commands to the CLI interface

### Running Tests

```bash
cd notes-cli
./gradlew test
```

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.