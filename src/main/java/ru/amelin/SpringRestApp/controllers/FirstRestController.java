package ru.amelin.SpringRestApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.amelin.SpringRestApp.Person;
import ru.amelin.SpringRestApp.utils.PersonErrorResponse;
import ru.amelin.SpringRestApp.utils.PersonNotFoundException;

import java.util.List;

@RestController //@Controller + @ResponseBody для каждого метода
@RequestMapping("/api")
public class FirstRestController {


    @GetMapping("/sayHello")

    public String sayHello() {
        return "Hello world!";
    }

    @GetMapping("/getPeople")
    public List<Person> getPersons() {
        Person p1 = new Person();
        p1.setName("ADFF");
        p1.setAge(55);
        Person p2 = new Person();
        p2.setName("GGGD");
        p2.setAge(233);
        //Jackson вернет json из коллекции
        return List.of(p1, p2);
    }

    @GetMapping("/person/{id}")
    public Person getPerson(@PathVariable("id") int id) {

        if (id % 11 == 0) {
            throw new PersonNotFoundException();
        }

        Person p1 = new Person();
        p1.setName("ADFF");
        p1.setAge(55);
        //Jackson вернет json из объекта
        return p1;
    }

    @PostMapping("/person/new")
    public ResponseEntity<HttpStatus> createPerson(@RequestBody Person person) {
        // по хорошему нужно использовать валидацию пришедшего объекта (Spring Validator)
        System.out.println("Person created!");
        return ResponseEntity.ok(HttpStatus.OK);//пустое тело и статус 200
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e){
        PersonErrorResponse response = new PersonErrorResponse(
                "User not found!",
                12
        );
        //в Http Ответе будет объект PersonErrorResponse в виде json и http status 400 (Bad Request)
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
