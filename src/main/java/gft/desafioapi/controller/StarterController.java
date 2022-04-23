package gft.desafioapi.controller;

import gft.desafioapi.entity.Starter;
import gft.desafioapi.repository.StarterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class StarterController {

    @Autowired
    private StarterRepository starterRepository;

    @GetMapping("/starter")
    @ResponseStatus(HttpStatus.OK)
    public List<Starter> listarStarter(){

        return starterRepository.findAll();

    }

    @PutMapping("/starter/{id}")
    public ResponseEntity<Starter> editarStarter(@RequestBody Starter starter, @PathVariable Long id) {

        try {
            Starter starters = starterRepository.getById(id);
            starterRepository.save(starter);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/starter")
    @ResponseStatus(HttpStatus.CREATED)
    public Starter salvarStarter(@RequestBody Starter starter){

        return starterRepository.save(starter);

    }

    @DeleteMapping("/starter/{id}")
    public ResponseEntity<Starter> deletarStarter(@PathVariable Long id){

        try {
            starterRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
