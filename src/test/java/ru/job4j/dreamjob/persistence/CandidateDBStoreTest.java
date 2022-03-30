package ru.job4j.dreamjob.persistence;

import org.junit.After;
import org.junit.Test;
import ru.job4j.dreamjob.config.DataBaseConnection;
import ru.job4j.dreamjob.model.Candidate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CandidateDBStoreTest {

    @After
    public void whenDeleteFrom() {
        CandidateDBStore store = new CandidateDBStore(new DataBaseConnection().loadPool());
        store.deleteFrom();
    }

    @Test
    public void whenCreatePost() {
        CandidateDBStore store = new CandidateDBStore(new DataBaseConnection().loadPool());
        store.deleteFrom();
        Candidate candidate = new Candidate(1, "Sam", "Spring, Java 17");
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), equalTo(candidate.getName()));
    }

    @Test
    public void whenUpdatePost() {
        CandidateDBStore store = new CandidateDBStore(new DataBaseConnection().loadPool());
        Candidate candidate = new Candidate(1, "Alex", "Spring, Java 17");
        store.add(candidate);
        Candidate candidateUpd = new Candidate(1, "Max", "Spring, Java 17");
        store.update(candidateUpd);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), equalTo(candidateUpd.getName()));
    }

    @Test
    public void whenFindAll() {
        CandidateDBStore store = new CandidateDBStore(new DataBaseConnection().loadPool());
        Candidate candidate1 = new Candidate(1, "Ivan", "Java 17");
        Candidate candidate2 = new Candidate(2, "Svetoslav", "Kotlin");
        store.add(candidate1);
        store.add(candidate2);
        List<Candidate> exp = List.of(new Candidate(1, "Ivan", "Java 17"),
                new Candidate(2, "Svetoslav", "Kotlin"));
        assertThat(store.findAll(), equalTo(exp));
    }
}