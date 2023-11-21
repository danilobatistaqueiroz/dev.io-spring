package nerdstore.core.communication;

public interface CommandSubscriber {
    <C extends ApplicationCommand> void onCommand(C command);
}
