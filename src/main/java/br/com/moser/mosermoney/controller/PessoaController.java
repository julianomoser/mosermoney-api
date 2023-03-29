package br.com.moser.mosermoney.controller;

import br.com.moser.mosermoney.event.RecursoCriadoEvent;
import br.com.moser.mosermoney.model.Pessoa;
import br.com.moser.mosermoney.security.CheckSecurity;
import br.com.moser.mosermoney.service.PessoaService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @CheckSecurity.Pessoa.PodeConsultar
    @GetMapping
    public Page<Pessoa> pesquisar(@RequestParam(required = false, defaultValue = "") String nome, Pageable pageable) {
        return service.findByNomeContaining(nome, pageable);
    }

    @CheckSecurity.Pessoa.PodeEditar
    @PostMapping
    public ResponseEntity<Pessoa> save(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        final Pessoa pessoaSalva = service.save(pessoa);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @CheckSecurity.Pessoa.PodeConsultar
    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
        return this.service.findById(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @CheckSecurity.Pessoa.PodeEditar
    @PutMapping(path = "/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo,
                                            @RequestBody @Valid Pessoa pessoa) {
        pessoa = service.atualizar(pessoa, codigo);
        return ResponseEntity.ok().body(pessoa);
    }

    @CheckSecurity.Pessoa.PodeEditar
    @PutMapping("/{codigo}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long codigo) {
        service.ativar(codigo);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pessoa.PodeEditar
    @DeleteMapping("/{codigo}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long codigo) {
        service.inativar(codigo);
        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pessoa.PodeRemover
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo) {
        this.service.deleteById(codigo);
    }
}
