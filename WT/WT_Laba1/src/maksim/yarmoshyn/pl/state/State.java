////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.pl.state;

/**
 * State interface.
 */
public interface State {

    /**
     * Print actions attached to that state.
     */
    void printActions();

    /**
     * Process action, that user choose.
     */
    void processAction();
}
