package nerdstore.core.communication;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import org.springframework.stereotype.Component;

import nerdstore.core.messages.commonmessages.domainevents.DomainEvent;
import nerdstore.core.messages.commonmessages.notifications.DomainNotification;
import nerdstore.core.messages.Event;

@Component
public class SimpleEventBus {
    private final Map<String, Set<EventSubscriber>> eventoInscritos = new ConcurrentHashMap<>();
    private final Map<String, Set<CommandSubscriber>> comandoInscritos = new ConcurrentHashMap<>();
    private final Map<String, Set<NotificationSubscriber>> notificacaoInscritos = new ConcurrentHashMap<>();
    private final Map<String, Set<DomainEventSubscriber>> eventoDeDominioInscritos = new ConcurrentHashMap<>();

    public SimpleEventBus(){
        
    }
    
    

    public <E extends Event> void publicarEvento(E event) {
        if (eventoInscritos.containsKey(event.getMessageType())) {
            eventoInscritos.get(event.getMessageType())
              .forEach(subscriber -> subscriber.onEvent(event));
        }
    }

    public <E extends Event> void subscribeToEvent(String eventType, EventSubscriber subscriber) {
        Set<EventSubscriber> evtSubs = eventoInscritos.get(eventType);
        if (evtSubs == null) {
            evtSubs = new CopyOnWriteArraySet<>();
            eventoInscritos.put(eventType, evtSubs);
        }
        evtSubs.add(subscriber);
    }

    public <E extends Event> void unsubscribeFromEvent(String eventType, EventSubscriber subscriber) {
        if (eventoInscritos.containsKey(eventType)) {
            eventoInscritos.get(eventType).remove(subscriber);
        }
    }
    
    

    public <C extends ApplicationCommand> void enviarComando(C command) {
        if (comandoInscritos.containsKey(command.getType())) {
            comandoInscritos.get(command.getType())
              .forEach(subscriber -> subscriber.onCommand(command));
        }
    }

    public <C extends ApplicationCommand> void subscribeToCommand(String cmdType, CommandSubscriber subscriber) {
        Set<CommandSubscriber> cmdSubs = comandoInscritos.get(cmdType);
        if (cmdSubs == null) {
            cmdSubs = new CopyOnWriteArraySet<>();
            comandoInscritos.put(cmdType, cmdSubs);
        }
        cmdSubs.add(subscriber);
    }

    public <C extends ApplicationCommand> void unsubscribeFromCommand(String cmdType, CommandSubscriber subscriber) {
        if (comandoInscritos.containsKey(cmdType)) {
            comandoInscritos.get(cmdType).remove(subscriber);
        }
    }
    
    


    public <N extends DomainNotification> void publicarNotificacao(N notificacao) {
        if (notificacaoInscritos.containsKey(notificacao.getKey())) {
            notificacaoInscritos.get(notificacao.getKey()).forEach(subscriber -> subscriber.onNotification(notificacao));
        }
    }

    public <N extends DomainNotification> void subscribeToNotification(String cmdType, NotificationSubscriber subscriber) {
        Set<NotificationSubscriber> cmdSubs = notificacaoInscritos.get(cmdType);
        if (cmdSubs == null) {
            cmdSubs = new CopyOnWriteArraySet<>();
            notificacaoInscritos.put(cmdType, cmdSubs);
        }
        cmdSubs.add(subscriber);
    }

    public <N extends DomainNotification> void unsubscribeFromNotification(String cmdType, NotificationSubscriber subscriber) {
        if (notificacaoInscritos.containsKey(cmdType)) {
            notificacaoInscritos.get(cmdType).remove(subscriber);
        }
    }
    
    

    
    
    public <D extends DomainEvent> void publicarDomainEvent(D domainEvent) {
        if (eventoDeDominioInscritos.containsKey(domainEvent.getMessageType())) {
        	eventoDeDominioInscritos.get(domainEvent.getMessageType())
              .forEach(subscriber -> subscriber.onDomainEvent(domainEvent));
        }
    }

    public <D extends DomainEvent> void subscribeToNotification(String cmdType, DomainEventSubscriber subscriber) {
        Set<DomainEventSubscriber> cmdSubs = eventoDeDominioInscritos.get(cmdType);
        if (cmdSubs == null) {
            cmdSubs = new CopyOnWriteArraySet<>();
            eventoDeDominioInscritos.put(cmdType, cmdSubs);
        }
        cmdSubs.add(subscriber);
    }

    public <D extends DomainEvent> void unsubscribeFromNotification(String cmdType, DomainEventSubscriber subscriber) {
        if (eventoDeDominioInscritos.containsKey(cmdType)) {
        	eventoDeDominioInscritos.get(cmdType).remove(subscriber);
        }
    }
    
}
