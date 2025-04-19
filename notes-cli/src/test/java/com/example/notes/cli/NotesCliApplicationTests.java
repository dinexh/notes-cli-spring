package com.example.notes.cli;

import com.example.notes.cli.model.Note;
import com.example.notes.cli.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class NotesCliApplicationTests {

	@Autowired
	private NoteRepository noteRepository;

	@Test
	void contextLoads() {
		assertThat(noteRepository).isNotNull();
	}

	@Test
	void testCreateNote() {
		// Create a new note
		Note note = new Note();
		note.setTitle("Test Note");
		note.setContent("This is a test note content");

		// Save the note
		Note savedNote = noteRepository.save(note);

		// Verify the note was saved
		assertThat(savedNote.getId()).isNotNull();
		assertThat(savedNote.getTitle()).isEqualTo("Test Note");
		assertThat(savedNote.getContent()).isEqualTo("This is a test note content");
		assertThat(savedNote.getCreatedAt()).isNotNull();
	}

	@Test
	void testReadNote() {
		// Create and save a note
		Note note = new Note();
		note.setTitle("Test Note");
		note.setContent("This is a test note content");
		Note savedNote = noteRepository.save(note);

		// Retrieve the note
		Optional<Note> foundNote = noteRepository.findById(savedNote.getId());

		// Verify the note was found
		assertThat(foundNote).isPresent();
		assertThat(foundNote.get().getTitle()).isEqualTo("Test Note");
		assertThat(foundNote.get().getContent()).isEqualTo("This is a test note content");
	}

	@Test
	void testUpdateNote() {
		// Create and save a note
		Note note = new Note();
		note.setTitle("Original Title");
		note.setContent("Original content");
		Note savedNote = noteRepository.save(note);

		// Update the note
		savedNote.setTitle("Updated Title");
		savedNote.setContent("Updated content");
		Note updatedNote = noteRepository.save(savedNote);

		// Verify the note was updated
		assertThat(updatedNote.getTitle()).isEqualTo("Updated Title");
		assertThat(updatedNote.getContent()).isEqualTo("Updated content");
	}

	@Test
	void testDeleteNote() {
		// Create and save a note
		Note note = new Note();
		note.setTitle("Test Note");
		note.setContent("This is a test note content");
		Note savedNote = noteRepository.save(note);

		// Delete the note
		noteRepository.deleteById(savedNote.getId());

		// Verify the note was deleted
		Optional<Note> foundNote = noteRepository.findById(savedNote.getId());
		assertThat(foundNote).isEmpty();
	}

	@Test
	void testNoteCreationTimestamp() {
		// Create a note
		Note note = new Note();
		note.setTitle("Test Note");
		note.setContent("Test content");

		// Save the note
		Note savedNote = noteRepository.save(note);

		// Verify the creation timestamp
		assertThat(savedNote.getCreatedAt()).isNotNull();
		assertThat(savedNote.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
	}
} 