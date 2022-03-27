package ru.job4j.dreamjob.persistence;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

@ThreadSafe
@Repository
public class CandidateStore {

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(0);

    public void add(Candidate candidate) {
        int newId = id.incrementAndGet();
        candidate.setId(newId);
        candidates.putIfAbsent(newId, candidate);
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidates.computeIfPresent(candidate.getId(), (key, value) -> {
            if (candidate.getId() != value.getId()) {
                throw new OptimisticException("Id is not equal");
            }
            return new Candidate(candidate.getId(), candidate.getName(),
                    candidate.getDescription());
        });
    }
}
