package com.stevo.qdsltest;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonRepo personRepository;

    public PersonController(PersonRepo personRepository) {
        this.personRepository = personRepository;

        Person p = new Person();
        p.setName("Stevo");
        personRepository.save(p);
    }

    @GetMapping
    public Iterable<Person> findAll(@RequestParam(required = false) String name) {
        QPerson p = QPerson.person;
        BooleanExpression predicate = p.isNotNull();

        if (StringUtils.hasText(name))
            predicate = predicate.and(p.name.contains(name));
        
        return personRepository.findAll(predicate);
    }
}
