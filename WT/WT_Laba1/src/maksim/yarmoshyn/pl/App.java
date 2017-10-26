////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.pl;

import maksim.yarmoshyn.bll.dto.UserDto;
import maksim.yarmoshyn.dal.dbcontext.DBContext;
import maksim.yarmoshyn.pl.state.AdminState;
import maksim.yarmoshyn.pl.state.ShowBookState;
import maksim.yarmoshyn.pl.state.State;
import maksim.yarmoshyn.pl.state.SuperAdminState;
import maksim.yarmoshyn.pl.state.UnauthorizedState;
import maksim.yarmoshyn.pl.state.UserState;

import java.io.IOException;

/**
 * Application class. Contain main functionality.
 */
public class App {

    /**
     * Database context.
     */
    private DBContext ctx;
    /**
     * User data.
     */
    private UserDto user;
    /**
     * Is application should work.
     */
    private boolean isWork = true;
    /**
     * Object for unauthorized state of application.
     */
    private State unauthorizedState;
    /**
     * Object for state of application when user has role User.
     */
    private State userState;
    /**
     * Object for state of application when user has role Admin.
     */
    private State adminState;
    /**
     * Object for state of application when user has role SuperAdmin.
     */
    private State superAdminState;
    /**
     * Object for state of application when list of books is showed.
     */
    private State showBookState;
    /**
     * Object for current state of application.
     */
    private State currentState;

    /**
     * Create new instance of {@link App}.
     *
     * @param context Database context.
     */
    public App(final DBContext context) {
        this.ctx = context;
        unauthorizedState = new UnauthorizedState(this);
        userState = new UserState(this);
        adminState = new AdminState(this);
        superAdminState = new SuperAdminState(this);
        showBookState = new ShowBookState(this);
        currentState = getUnauthorizedState();
    }

    /**
     * Get state for unauthorized user.
     *
     * @return State for unauthorized user.
     */
    public State getUnauthorizedState() {
        return unauthorizedState;
    }

    /**
     * Get state for user.
     *
     * @return State for user.
     */
    public State getUserState() {
        return userState;
    }

    /**
     * Get state for admin.
     *
     * @return State for admin.
     */
    public State getAdminState() {
        return adminState;
    }

    /**
     * Get state for SuperAdmin.
     *
     * @return State for SuperAdmin.
     */
    public State getSuperAdminState() {
        return superAdminState;
    }

    /**
     * Get state for showing book.
     *
     * @return State for showing book.
     */
    public State getShowBookState() {
        return showBookState;
    }

    /**
     * Set current state.
     *
     * @param state State to set.
     */
    public void setCurrentState(final State state) {
        this.currentState = state;
    }

    /**
     * Set state for current user.
     */
    public void returnToMenu() {
        if (user == null) {
            setCurrentState(getUnauthorizedState());
            return;
        }

        switch (user.getRole()) {
            case SUPER_ADMIN:
                setCurrentState(getSuperAdminState());
                break;
            case ADMIN:
                setCurrentState(getAdminState());
                break;
            case USER:
                setCurrentState(getUserState());
                break;
        }
    }

    /**
     * Get database context.
     *
     * @return Database context.
     */
    public DBContext getCtx() {
        return ctx;
    }

    /**
     * Get user data.
     *
     * @return User data.
     */
    public UserDto getUser() {
        return user;
    }

    /**
     * Set user data.
     *
     * @param us User data.
     */
    public void setUser(final UserDto us) {
        this.user = us;
    }

    /**
     * Set is application should work.
     *
     * @param work Is application should work.
     */
    public void setIsWork(final boolean work) {
        this.isWork = work;
    }

    /**
     * Start App.
     */
    public void start() {
        cls();
        while (isWork) {
            currentState.printActions();
            currentState.processAction();
        }
    }

    /**
     * Clear screen.
     */
    private void cls() {
        try {
            new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
