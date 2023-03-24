package br.com.moser.mosermoney.event.listner;

import br.com.moser.mosermoney.event.RecursoCriadoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

/**
 * @author Juliano Moser
 */
@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {
    @Override
    public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {
        HttpServletResponse response = recursoCriadoEvent.getResponse();
        Long codigo = recursoCriadoEvent.getCodigo();

        addHeaderLocation(response, codigo);
    }

    private void addHeaderLocation(HttpServletResponse response, Long codigo) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{codigo}")
                .buildAndExpand(codigo).toUri();
        response.setHeader(HttpHeaders.LOCATION, uri.toString());
    }
}
