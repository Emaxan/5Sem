package by.bsuir.MY.domain;

import by.bsuir.MY.domain.Interf.ServiceResponseCode;

/**
 * .
 *
 * @param <TContent> .
 */
public class ServiceResponseGeneric<TContent>
        extends ServiceResponse
        implements by.bsuir.MY.domain.Interf.ServiceResponseGeneric {
    /**
     * .
     *
     * @param code    .
     * @param content .
     */
    protected ServiceResponseGeneric(final ServiceResponseCode code, final TContent content) {
        super(code, content);
    }


    /**
     * .
     *
     * @param code .
     */
    protected ServiceResponseGeneric(final ServiceResponseCode code) {
        super(code);
    }

    /**
     * .
     *
     * @return .
     */
    @Override
    public TContent getContent() {
        return (TContent) super.getContent();
    }
}
