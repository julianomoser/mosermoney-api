package br.com.moser.mosermoney.repository.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * @author Juliano Moser
 */
@Getter
@Setter
public class LancamentoFilter {

    private String descricao;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataVencimentoIncio;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataVencimentoFim;
}
