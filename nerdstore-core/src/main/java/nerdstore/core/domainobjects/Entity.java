package nerdstore.core.domainobjects;

import java.util.*;
import nerdstore.core.messages.Event;
import lombok.ToString;

@ToString
public class Entity {

    private List<Event> _notificacoes = new ArrayList<Event>();
    public List<Event> notificacoes;

    public Entity(){
        notificacoes = Collections.unmodifiableList(_notificacoes);
    }
    public Boolean ehValido() {
        throw new RuntimeException();
    }
    public void adicionarEvento(Event evento) {
        if(notificacoes==null)
            notificacoes = new ArrayList<Event>();
        notificacoes.add(evento);
    }

    public void removerEvento(Event eventItem) {
        if(notificacoes!=null)
            notificacoes.remove(eventItem);
    }

    public void limparEventos() {
        if(notificacoes!=null)
            notificacoes.clear();
    }
}