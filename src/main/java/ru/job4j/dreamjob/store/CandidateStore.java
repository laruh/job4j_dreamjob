package ru.job4j.dreamjob.store;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ru.job4j.dreamjob.model.Candidate;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    public CandidateStore() {
        candidates.put(1, new Candidate(1, "Java Backend", "Spring Boot, SQL"));
        candidates.put(2, new Candidate(2, "Software Engineer", "Java, AWS"));
        candidates.put(3, new Candidate(3, "Java Architect", "Microservices, Spring"));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
