import java.util.Comparator;

class EventComp implements Comparator<Event> {

    @Override
    public int compare(Event e1, Event e2) {
        return e1.compareTo(e2);
    }
}

