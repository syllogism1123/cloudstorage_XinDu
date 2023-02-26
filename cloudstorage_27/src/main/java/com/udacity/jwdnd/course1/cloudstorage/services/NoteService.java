package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getNotesByUser(Integer userId) {
        return this.noteMapper.getNotesByUser(userId);
    }

    public void createNote(Note newNote) {
        this.noteMapper.insertNote(newNote);
    }

    public void update(Note newNote) {
        this.noteMapper.updateNote(newNote);
    }

    public void deleteNote(Integer noteId) {
        this.noteMapper.deleteNote(noteId);
    }

}


/*  @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getNotesByUser(Integer userId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) " +
            "VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer addNote(Note newNote);

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription}, userId=#{userId} " +
            "WHERE noteId = #{noteId}")
    Integer updateNote(Note newNote);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    void deleteNote(Integer noteId);
 */