package com.example.notes.cli.commands;

import com.example.notes.cli.model.Note;
import com.example.notes.cli.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
public class NoteCommands {

    private final NoteService noteService;

    @Autowired
    public NoteCommands(NoteService noteService) {
        this.noteService = noteService;
    }

    @ShellMethod(key = "list", value = "List all notes")
    public List<Note> listNotes() {
        return noteService.getAllNotes();
    }

    @ShellMethod(key = "get", value = "Get a note by ID")
    public Note getNote(@ShellOption(help = "ID of the note") Long id) {
        return noteService.getNoteById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
    }

    @ShellMethod(key = "create", value = "Create a new note")
    public Note createNote(
            @ShellOption(help = "Title of the note") String title,
            @ShellOption(help = "Content of the note") String content) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        return noteService.createNote(note);
    }

    @ShellMethod(key = "update", value = "Update an existing note")
    public Note updateNote(
            @ShellOption(help = "ID of the note to update") Long id,
            @ShellOption(help = "New title of the note") String title,
            @ShellOption(help = "New content of the note") String content) {
        Note noteDetails = new Note();
        noteDetails.setTitle(title);
        noteDetails.setContent(content);
        return noteService.updateNote(id, noteDetails);
    }

    @ShellMethod(key = "delete", value = "Delete a note")
    public String deleteNote(@ShellOption(help = "ID of the note to delete") Long id) {
        noteService.deleteNote(id);
        return "Note deleted successfully";
    }
} 