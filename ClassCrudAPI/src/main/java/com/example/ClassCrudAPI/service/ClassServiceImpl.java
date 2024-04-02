package com.example.ClassCrudAPI.service;

import com.example.ClassCrudAPI.dao.ClassRespository;
import com.example.ClassCrudAPI.entity.Class;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassServiceImpl implements ClassService{

    private ClassRespository classRespository;

    @Autowired
    public ClassServiceImpl (ClassRespository theClassRespository) {
        classRespository = theClassRespository;
    }

    @Override
    public Class save(Class theClass) {
        return classRespository.save(theClass);
    }

    @Override
    public List<Class> findAll() {
        return classRespository.findAll();
    }

    @Override
    public Class findById(String theId) {
        Optional<Class> result = classRespository.findById(theId);
        Class theClass = null;
        if(result.isPresent()) {
            theClass = result.get();
        }
        else {
            throw new RuntimeException("Didn't find class id - " + theId);
        }
        return theClass;
    }


    @Override
    public void deleteById(String theId) {
        classRespository.deleteById(theId);
    }

    @Override
    public Page<Class> findByName(String theName, Pageable pageable) {
        return classRespository.findByNameStartingWith(theName, pageable);
    }


}
