package br.com.moser.mosermoney.assembler;

import br.com.moser.mosermoney.model.Lancamento;
import br.com.moser.mosermoney.model.projection.LancamentoResumoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Juliano Moser
 */
@Component
public class LancamentoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public LancamentoResumoDTO toModel(Lancamento restaurante) {
        return modelMapper.map(restaurante, LancamentoResumoDTO.class);
    }

    public List<LancamentoResumoDTO> toCollectionModel(List<Lancamento> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toModel(restaurante))
                .collect(Collectors.toList());
    }
}
