package run;

import view.AttView;
import evt.AttEvent;

public class run {
    public static void main(String[] args) {
        AttView view = new AttView();
        new AttEvent(view);
    }
}
