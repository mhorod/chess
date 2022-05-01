package app.ui.board.state;

public abstract class State implements Behavior {
    private Machine machine;

    final protected void changeState(State newState) {
        machine.changeState(newState);
    }

    final void loadMachine(Machine machine) {
        this.machine = machine;
    }

    protected void init() {
    }

    protected void cleanUp() {
    }
}
