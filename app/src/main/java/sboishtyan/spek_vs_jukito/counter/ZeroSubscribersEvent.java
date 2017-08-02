package sboishtyan.spek_vs_jukito.counter;

/**
 * @author Sergey Boishtyan
 */
public final class ZeroSubscribersEvent {

    private final static ZeroSubscribersEvent event = new ZeroSubscribersEvent();

    private ZeroSubscribersEvent() {
        //empty
    }

    public static ZeroSubscribersEvent event() {
        return event;
    }
}
