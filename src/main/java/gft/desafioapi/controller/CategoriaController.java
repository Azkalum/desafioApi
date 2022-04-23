package gft.desafioapi.controller;

import gft.desafioapi.entity.Categoria;
import gft.desafioapi.entity.Starter;
import gft.desafioapi.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class CategoriaController {

    @Autowired
    CategoriaRepository categoriaRepository;

    @GetMapping("/categoria")
    @ResponseStatus(HttpStatus.OK)
    public List<Categoria> listarStarter(){

        return categoriaRepository.findAll();

    }

    @PostMapping("/categoria")
    public ResponseEntity<Categoria> adicionar(@RequestBody Categoria categoria) {
        categoriaRepository.save(categoria);
        return new ResponseEntity<>(categoria, HttpStatus.CREATED);

    }

    @GetMapping(path = "/categoria/{id}")
    public ResponseEntity<Optional<Categoria>> getById(@PathVariable Long id) {
        Optional<Categoria> categoria;
        try {
            categoria = categoriaRepository.findById(id);
            return new ResponseEntity<Optional<Categoria>>(categoria, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Optional<Categoria>>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/categoria/{id}")
    public ResponseEntity<Starter> editarCategoria(@RequestBody Categoria categoria, @PathVariable Long id) {

        try {
            Categoria categoria1 = categoriaRepository.getById(id);
            categoriaRepository.save(categoria);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(path = "/categoria/{id}")
    public ResponseEntity<Optional<Categoria>> deletar(@PathVariable Long id) {
        try {
            categoriaRepository.deleteById(id);
            return new ResponseEntity<Optional<Categoria>>(HttpStatus.OK);
        } catch (NoSuchElementException nsee) {
            return new ResponseEntity<Optional<Categoria>>(HttpStatus.NOT_FOUND);
        }
    }

}
