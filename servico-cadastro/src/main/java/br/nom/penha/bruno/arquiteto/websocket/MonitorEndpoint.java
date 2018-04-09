package br.nom.penha.bruno.arquiteto.websocket;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import br.nom.penha.bruno.arquiteto.business.events.EventoProduto;
import br.nom.penha.bruno.arquiteto.json.converter.ProdutoJsonConverter;
import br.nom.penha.bruno.arquiteto.model.Produto;

/**
 *
 * @author Arquiteto
 */
@Dependent
@ServerEndpoint("/monitor")
public class MonitorEndpoint {

    private static final Set<Session> monitores = ConcurrentHashMap.newkeySet();

    @OnOpen
    public void init(Session session) {
        monitores.add(session);
        System.out.println("Sessao adicionada a lista de monitores " + session.getId());
    }

    public void notificaMonitores(@Observes EventoProduto evento) throws IOException {
        Produto prod = evento.getProduto();
        JsonObject produtoJson = Json.createObjectBuilder()
                .add("evento",evento.getTipo().toString())
                .add("produto", ProdutoJsonConverter.convertToJson(prod))
                .build();
        for (Session monitor : monitores) {
                monitor.getBasicRemote().sendText(produtoJson.toString());
        }
    }

    @OnClose
    public void destroy(Session session) {
        monitores.remove(session);
        System.out.println("Sessao removida da lista de monitores " + session.getId());
    }

}
