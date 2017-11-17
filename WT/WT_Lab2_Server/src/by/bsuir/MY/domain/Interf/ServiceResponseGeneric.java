package by.bsuir.MY.domain.Interf;

/**
 * .
 *
 * @param <TContent> .
 */
public interface ServiceResponseGeneric<TContent> extends ServiceResponse {
    /**
     * .
     *
     * @return .
     */
    TContent getContent();
}
