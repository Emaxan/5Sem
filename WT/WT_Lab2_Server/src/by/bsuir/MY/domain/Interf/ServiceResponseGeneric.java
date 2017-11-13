package by.bsuir.MY.domain.Interf;

/**
 * TODO.
 *
 * @param <TContent> TODO.
 */
public interface ServiceResponseGeneric<TContent> extends ServiceResponse {
    /**
     * TODO.
     *
     * @return TODO.
     */
    TContent getContent();
}
