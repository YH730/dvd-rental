package model;

/**
 * Borrow status of a film
 *
 * @param AVAILABLE Film is available
 * @param UNAVAILABLE Film has no borrowable copies left
 */
public enum State {
    AVAILABLE("AVAILABLE"),
    UNAVAILABLE("UNAVAILABLE");

    private String status;
    State(String status){
        this.status = status;
    }

    public String toString(){
        return status;
    }
}

