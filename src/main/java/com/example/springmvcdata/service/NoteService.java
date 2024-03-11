package com.example.springmvcdata.service;

import com.example.springmvcdata.domain.Note;
import com.example.springmvcdata.model.NoteDTO;
import com.example.springmvcdata.repos.NoteRepository;
import com.example.springmvcdata.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(final NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteDTO> findAll() {
        final List<Note> notes = noteRepository.findAll(Sort.by("id"));
        return notes.stream()
                .map(note -> mapToDTO(note, new NoteDTO()))
                .toList();
    }

    public NoteDTO get(final Long id) {
        return noteRepository.findById(id)
                .map(note -> mapToDTO(note, new NoteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final NoteDTO noteDTO) {
        final Note note = new Note();
        mapToEntity(noteDTO, note);
        return noteRepository.save(note).getId();
    }

    public void update(final Long id, final NoteDTO noteDTO) {
        final Note note = noteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(noteDTO, note);
        noteRepository.save(note);
    }

    public void delete(final Long id) {
        noteRepository.deleteById(id);
    }

    private NoteDTO mapToDTO(final Note note, final NoteDTO noteDTO) {
        noteDTO.setId(note.getId());
        noteDTO.setTitle(note.getTitle());
        noteDTO.setContent(note.getContent());
        return noteDTO;
    }

    private Note mapToEntity(final NoteDTO noteDTO, final Note note) {
        note.setTitle(noteDTO.getTitle());
        note.setContent(noteDTO.getContent());
        return note;
    }

}
