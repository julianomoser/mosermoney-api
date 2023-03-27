package br.com.moser.mosermoney.controller;

import br.com.moser.mosermoney.event.RecursoCriadoEvent;
import br.com.moser.mosermoney.model.Categoria;
import br.com.moser.mosermoney.security.CheckSecurity;
import br.com.moser.mosermoney.service.CategoriaService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Juliano Moser
 */
@RestController
@RequestMapping(path = "/categorias")
public class CatergoriaController {

    private final CategoriaService service;
    private final ApplicationEventPublisher publisher;

    public CatergoriaController(CategoriaService service, ApplicationEventPublisher publisher) {
        this.service = service;
        this.publisher = publisher;
    }

    @CheckSecurity.Categoria.PodeConsultar
    @GetMapping
    public ResponseEntity<List<Categoria>> listAll() {
        final List<Categoria> categoriaList = service.listAll();
        return ResponseEntity.ok(categoriaList);
    }

    @CheckSecurity.Categoria.PodeEditar
    @PostMapping
    public ResponseEntity<Categoria> save(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
        final Categoria categoriaSalva = service.save(categoria);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @CheckSecurity.Categoria.PodeConsultar
    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
        return this.service.findById(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
