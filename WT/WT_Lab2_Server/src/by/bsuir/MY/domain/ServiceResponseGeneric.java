package by.bsuir.MY.domain;

import by.bsuir.MY.domain.Interf.ServiceResponseCode;

/**
 * TODO.
 *
 * @param <TContent> TODO.
 */
public class ServiceResponseGeneric<TContent>
        extends ServiceResponse
        implements by.bsuir.MY.domain.Interf.ServiceResponseGeneric {
    /**
     * TODO.
     *
     * @param code    TODO.
     * @param content TODO.
     */
    protected ServiceResponseGeneric(final ServiceResponseCode code, final TContent content) {
        super(code, content);
    }


    /**
     * TODO.
     *
     * @param code TODO.
     */
    protected ServiceResponseGeneric(final ServiceResponseCode code) {
        super(code);
    }

    /**
     * TODO.
     *
     * @return TODO.
     */
    @Override
    public TContent getContent() {
        return (TContent) super.getContent();
    }
}
