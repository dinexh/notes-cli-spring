#!/bin/bash

# Function to display help
show_help() {
  echo "Notes CLI - A simple command-line interface for managing notes"
  echo ""
  echo "Usage:"
  echo "  ./notes.sh [command] [options]"
  echo ""
  echo "Commands:"
  echo "  list                  List all notes"
  echo "  get <id>              Get a note by ID"
  echo "  create <title> <content>  Create a new note"
  echo "  update <id> <title> <content>  Update an existing note"
  echo "  delete <id>           Delete a note"
  echo "  help                  Show this help message"
  echo ""
  echo "Examples:"
  echo "  ./notes.sh list"
  echo "  ./notes.sh create \"My Note\" \"This is the content\""
  echo "  ./notes.sh get 1"
  echo "  ./notes.sh update 1 \"Updated Title\" \"Updated content\""
  echo "  ./notes.sh delete 1"
}

# Check if the application is running
check_app_running() {
  if ! curl -s http://localhost:8080/api/notes > /dev/null; then
    echo "Starting the notes application..."
    cd "$(dirname "$0")" && ./gradlew bootRun &
    sleep 5  # Wait for the application to start
  fi
}

# Main script
if [ "$1" == "help" ] || [ "$1" == "-h" ] || [ "$1" == "--help" ]; then
  show_help
  exit 0
fi

# Check if the application is running
check_app_running

# Process commands
case "$1" in
  "list")
    curl -s http://localhost:8080/api/notes | jq '.'
    ;;
  "get")
    if [ -z "$2" ]; then
      echo "Error: Note ID is required"
      exit 1
    fi
    curl -s http://localhost:8080/api/notes/$2 | jq '.'
    ;;
  "create")
    if [ -z "$2" ] || [ -z "$3" ]; then
      echo "Error: Title and content are required"
      exit 1
    fi
    curl -s -X POST http://localhost:8080/api/notes \
      -H "Content-Type: application/json" \
      -d "{\"title\":\"$2\",\"content\":\"$3\"}" | jq '.'
    ;;
  "update")
    if [ -z "$2" ] || [ -z "$3" ] || [ -z "$4" ]; then
      echo "Error: ID, title, and content are required"
      exit 1
    fi
    curl -s -X PUT http://localhost:8080/api/notes/$2 \
      -H "Content-Type: application/json" \
      -d "{\"title\":\"$3\",\"content\":\"$4\"}" | jq '.'
    ;;
  "delete")
    if [ -z "$2" ]; then
      echo "Error: Note ID is required"
      exit 1
    fi
    curl -s -X DELETE http://localhost:8080/api/notes/$2
    echo "Note deleted successfully"
    ;;
  *)
    echo "Unknown command: $1"
    show_help
    exit 1
    ;;
esac 