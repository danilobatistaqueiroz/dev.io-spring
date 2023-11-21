package nerdstore.core.communication;
import nerdstore.core.messages.Event;

public interface EventSubscriber {
    <E extends Event> void onEvent(E event);
}
