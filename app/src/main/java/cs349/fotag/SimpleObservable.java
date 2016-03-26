package cs349.fotag;

import java.util.Observable;

public class SimpleObservable extends Observable {

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }

    @Override
    public void notifyObservers(Object data) {
        setChanged();
        super.notifyObservers(data);
    }
}
