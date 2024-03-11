package com.example.springmvcdata.repos;

import com.example.springmvcdata.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NoteRepository extends JpaRepository<Note, Long> {
}
