package br.com.moser.mosermoney.controller;

import br.com.moser.mosermoney.event.RecursoCriadoEvent;
import br.com.moser.mosermoney.model.Pessoa;
import br.com.moser.mosermoney.service.PessoaService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Juliano Moser
 */
@RestController
@RequestMapping(path = "/pessoas")
public class PessoaController {

    private final PessoaService service;
    private final ApplicationEventPublisher publisher;

    public PessoaController(PessoaService service, ApplicationEventPublisher publisher) {
        this.service = service;
        this.publisher = publisher;
    }

    @PostMapping
    public ResponseEntity<Pessoa> save(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        final Pessoa pessoaSalva = service.save(pessoa);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
        return this.service.findById(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo) {
        this.service.deleteById(codigo);
    }
}
