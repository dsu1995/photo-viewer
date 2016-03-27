package cs349.fotag;

public class ChangeEvent {
    public enum Event {
        ADD, REMOVE, REFILTER_ALL, RATING_CHANGED
    }
    public Event event;
    public int index;

    public ChangeEvent(Event event, int index) {
        this.event = event;
        this.index = index;
    }

    public ChangeEvent(Event event) {
        this.event = event;
    }
}
