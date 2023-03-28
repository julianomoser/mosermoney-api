package br.com.moser.mosermoney.controller;

import br.com.moser.mosermoney.assembler.LancamentoModelAssembler;
import br.com.moser.mosermoney.event.RecursoCriadoEvent;
import br.com.moser.mosermoney.model.Lancamento;
import br.com.moser.mosermoney.model.projection.LancamentoResumoDTO;
import br.com.moser.mosermoney.repository.LancamentoRepository;
import br.com.moser.mosermoney.repository.filter.LancamentoFilter;
import br.com.moser.mosermoney.repository.spec.LancamentoSpecs;
import br.com.moser.mosermoney.security.CheckSecurity;
import br.com.moser.mosermoney.service.LancamentoService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping(path = "/lancamentos")
public class LancamentoController {

    private final LancamentoService service;
    private final ApplicationEventPublisher publisher;
    private final LancamentoRepository repository;
    private final LancamentoModelAssembler lancamentoModelAssembler;

    public LancamentoController(LancamentoService service, ApplicationEventPublisher publisher, LancamentoRepository repository, LancamentoModelAssembler lancamentoModelAssembler) {
        this.service = service;
        this.publisher = publisher;
        this.repository = repository;
        this.lancamentoModelAssembler = lancamentoModelAssembler;
    }

    @CheckSecurity.Lancamento.PodeConsultar
    @GetMapping
    public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter,
                                      @PageableDefault(size = 10) Pageable pageable) {
        return repository.findAll(LancamentoSpecs.usingFilter(lancamentoFilter), pageable);
    }

    @CheckSecurity.Lancamento.PodeConsultar
    @GetMapping(params = "resumo")
    public Page<LancamentoResumoDTO> resumo(LancamentoFilter lancamentoFilter,
                                            @PageableDefault(size = 10) Pageable pageable) {
        final Page<Lancamento> lancamentosPage = repository.findAll(LancamentoSpecs.usingFilter(lancamentoFilter), pageable);

        List<LancamentoResumoDTO> LancamentoResumoModel = lancamentoModelAssembler
                .toCollectionModel(lancamentosPage.getContent());

        return new PageImpl<>(LancamentoResumoModel, pageable,
                lancamentosPage.getTotalElements());
    }

    @CheckSecurity.Lancamento.PodeConsultar
    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
        return this.service.findById(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @CheckSecurity.Lancamento.PodeEditar
    @PostMapping
    public ResponseEntity<Lancamento> save(@Valid @RequestBody Lancamento Lancamento, HttpServletResponse response) {
        final Lancamento LancamentoSalva = service.save(Lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, LancamentoSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(LancamentoSalva);
    }

    @CheckSecurity.Lancamento.PodeEditar
    @PutMapping(path = "/{codigo}")
    public ResponseEntity<Lancamento> atualizar(@PathVariable Long codigo,
                                                @RequestBody @Valid Lancamento Lancamento) {
        Lancamento = service.atualizar(Lancamento, codigo);
        return ResponseEntity.ok().body(Lancamento);
    }

    @CheckSecurity.Lancamento.PodeRemover
    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo) {
        this.service.deleteById(codigo);
    }
}
