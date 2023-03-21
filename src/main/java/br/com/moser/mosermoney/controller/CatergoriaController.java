package br.com.moser.mosermoney.controller;

import br.com.moser.mosermoney.model.Categoria;
import br.com.moser.mosermoney.service.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Juliano Moser
 */
@RestController
@RequestMapping(path = "/categorias")
public class CatergoriaController {

    private final CategoriaService service;

    public CatergoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listAll() {
        final List<Categoria> categoriaList = service.listAll();
        return ResponseEntity.ok(categoriaList);
    }
}
