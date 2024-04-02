package com.example.ClassCrudAPI.dao;

import com.example.ClassCrudAPI.entity.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClassRespository extends JpaRepository<Class, String> {
    Page<Class> findByNameStartingWith(String name, Pageable pageable);
}
