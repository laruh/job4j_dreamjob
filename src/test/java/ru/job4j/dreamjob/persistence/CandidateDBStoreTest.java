package ru.job4j.dreamjob.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.dreamjob.model.Candidate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
class CandidateDBStoreTest {

    @Autowired
    private CandidateDBStore store;

    @BeforeEach
    public void whenDeleteFrom() {
        store.deleteFrom();
    }

    @Test
    @DisplayName("Запись кандидата корректно создается в бд")
    void whenCreateCandidate() {
        store.deleteFrom();
        Candidate candidate = new Candidate(1, "Sam", "Spring, Java 17");
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), equalTo(candidate.getName()));
    }

    @Test
    @DisplayName("Запись кандидата корректно редактируется в бд")
    void whenUpdateCandidate() {
        Candidate candidate = new Candidate(1, "Alex", "Spring, Java 17");
        store.add(candidate);
        Candidate candidateUpd = new Candidate(1, "Max", "Spring, Java 17");
        store.update(candidateUpd);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), equalTo(candidateUpd.getName()));
    }

    @Test
    @DisplayName("Корректный сбор всех кандидатов в список из бд")
    void whenFindAll() {
        Candidate candidate1 = new Candidate(1, "Ivan", "Java 17");
        Candidate candidate2 = new Candidate(2, "Svetoslav", "Kotlin");
        store.add(candidate1);
        store.add(candidate2);
        List<Candidate> exp = List.of(new Candidate(1, "Ivan", "Java 17"),
                new Candidate(2, "Svetoslav", "Kotlin"));
        assertThat(store.findAll(), equalTo(exp));
    }
}