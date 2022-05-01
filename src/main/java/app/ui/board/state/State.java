package app.ui.board.state;


public abstract class State<P extends app.ui.board.Piece<?, ?>> implements Behavior<P> {
    private Machine<P> machine;

    final protected void changeState(State<P> newState) {
        machine.changeState(newState);
    }

    final void loadMachine(Machine<P> machine) {
        this.machine = machine;
    }

    protected void init() {
    }

    protected void cleanUp() {
    }
}
