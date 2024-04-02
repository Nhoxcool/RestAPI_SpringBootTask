package com.example.ClassCrudAPI.service;

import com.example.ClassCrudAPI.entity.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClassService {
    Class save(Class theClass);
    List<Class> findAll();
    Class findById(String theId);

    void deleteById(String theId);

    Page<Class> findByName(String theName, Pageable pageable);
}
