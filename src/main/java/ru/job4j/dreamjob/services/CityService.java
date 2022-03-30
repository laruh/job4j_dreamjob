package ru.job4j.dreamjob.services;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.persistence.CityDBStore;

import java.util.List;

@Service
public class CityService {
    private final CityDBStore store;

    public CityService(CityDBStore store) {
        this.store = store;
    }

    public List<City> getAllCities() {
        return store.findAll();
    }

    public City findById(int id) {
        return store.findById(id);
    }
}
