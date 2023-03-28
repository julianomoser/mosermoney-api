package br.com.moser.mosermoney.model.projection;

import br.com.moser.mosermoney.model.TipoLancamento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Juliano Moser
 */
@Getter
@Setter
public class LancamentoResumoDTO {

    private Long codigo;
    private String descricao;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private TipoLancamento tipo;
    private CategoriaNomeDTO categoria;
    private PessoaApenasNomeDTO pessoa;
}
