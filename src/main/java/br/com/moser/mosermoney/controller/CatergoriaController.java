package br.com.moser.mosermoney.controller;

import br.com.moser.mosermoney.model.Categoria;
import br.com.moser.mosermoney.service.CategoriaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
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

    @PostMapping
    public ResponseEntity<Categoria> save(@RequestBody Categoria categoria, HttpServletResponse response) {
        final Categoria categoriaSalva = service.save(categoria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(categoriaSalva.getCodigo()).toUri();
        response.setHeader(HttpHeaders.LOCATION, uri.toString());
        return ResponseEntity.created(uri).body(categoriaSalva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
        return this.service.findById(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
