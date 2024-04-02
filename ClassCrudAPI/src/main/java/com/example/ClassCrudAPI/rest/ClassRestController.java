package com.example.ClassCrudAPI.rest;

import com.example.ClassCrudAPI.entity.Class;
import com.example.ClassCrudAPI.service.ClassService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClassRestController {
    private ClassService classService;

    public ClassRestController(ClassService theClassService) {
        classService = theClassService;
    }

    @GetMapping("/classes")
    public List<Class> findAll() {return classService.findAll();}

    @PostMapping("/classes")
    public Class addClass(@RequestBody Class theClass) {
        // Set current Date
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        theClass.setCreatedDate(currentDate);
        theClass.setUpdatedDate(currentDate);

        List<Class> classList = classService.findAll();
        // Check class id existing or not
        for(Class c: classList) {
            if (theClass.getId().equals(c.getId())){
                throw new RuntimeException("Class id is existing...");
            };
        };

        // check Class name is existing or not
        for(Class c: classList) {
            if (theClass.getName().equals(c.getName())){
                throw new RuntimeException("Class name is existing: " + theClass.getName());
            };
        };


        Class dbClass = classService.save(theClass);
        return dbClass;
    }

    //Get Detail Class by id transaction
    @GetMapping("/classes/{classId}")
    public Class getClass(@PathVariable String classId) {
        Class theClass = classService.findById(classId);

        if(theClass == null) {
            throw new RuntimeException("class id not found -" + classId);
        }

        return theClass;
    }

    // update existing class
    @PutMapping("/classes/{classId}")
    public Class updateClass(@RequestBody Class theClass, @PathVariable String classId) {

        List<Class> classList = classService.findAll();
        boolean check = false;

        for(Class c: classList) {
            if(classId.equals(c.getId())) {
                check = true;
                theClass.setCreatedDate(c.getCreatedDate());
                theClass.setId(classId);
            }
        }

        if(check)
        {
            Calendar calendar = Calendar.getInstance();
            Date currentDate = calendar.getTime();
            theClass.setUpdatedDate(currentDate);

            Class dbClass = classService.save(theClass);
            return dbClass;
        }
       else
        {
            throw new RuntimeException("Class id isn't exist");
        }
    }

    //Delted by id
    @DeleteMapping("/classes/{classId}")
    public String deleteClass(@PathVariable String classId){
        Class theClass = classService.findById(classId);

        if(theClass == null) {
            throw new RuntimeException("Class id not found -" + classId);
        }

        classService.deleteById(classId);

        return "Deleted class id successfully";
    }

    @GetMapping("/classes/search")
    public Page<Class> searchClassByName(@RequestParam(defaultValue = "") String name,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "name") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Class> classes = classService.findByName(name, pageable);
        if(classes.isEmpty()) {
            throw new RuntimeException("Class not found");
        }

        return classes;
    }
}
