package br.com.moser.mosermoney.controller;

import br.com.moser.mosermoney.event.RecursoCriadoEvent;
import br.com.moser.mosermoney.model.Lancamento;
import br.com.moser.mosermoney.repository.LancamentoRepository;
import br.com.moser.mosermoney.repository.filter.LancamentoFilter;
import br.com.moser.mosermoney.repository.spec.LancamentoSpecs;
import br.com.moser.mosermoney.service.LancamentoService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Juliano Moser
 */
@RestController
@RequestMapping(path = "/lancamentos")
public class LancamentoController {

    private final LancamentoService service;
    private final ApplicationEventPublisher publisher;
    private final LancamentoRepository repository;

    public LancamentoController(LancamentoService service, ApplicationEventPublisher publisher, LancamentoRepository repository) {
        this.service = service;
        this.publisher = publisher;
        this.repository = repository;
    }

    @GetMapping
    public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter,
                                      @PageableDefault(size = 10) Pageable pageable) {
        return repository.findAll(LancamentoSpecs.usingFilter(lancamentoFilter), pageable);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
        return this.service.findById(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Lancamento> save(@Valid @RequestBody Lancamento Lancamento, HttpServletResponse response) {
        final Lancamento LancamentoSalva = service.save(Lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, LancamentoSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(LancamentoSalva);
    }

    @PutMapping(path = "/{codigo}")
    public ResponseEntity<Lancamento> atualizar(@PathVariable Long codigo,
                                                @RequestBody @Valid Lancamento Lancamento) {
        Lancamento = service.atualizar(Lancamento, codigo);
        return ResponseEntity.ok().body(Lancamento);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo) {
        this.service.deleteById(codigo);
    }
}
