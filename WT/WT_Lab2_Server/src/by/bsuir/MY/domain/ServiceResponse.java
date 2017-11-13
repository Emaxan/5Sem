package by.bsuir.MY.domain;

import by.bsuir.MY.domain.Interf.ServiceResponseCode;
import by.bsuir.MY.domain.exception.ArgumentException;
import org.jetbrains.annotations.NotNull;

/**
 * TODO.
 */
public class ServiceResponse
        implements by.bsuir.MY.domain.Interf.ServiceResponse {
    /**
     * TODO.
     */
    private ServiceResponseCode responseCode;
    /**
     * TODO.
     */
    private Object responseContent;

    /**
     * TODO.
     *
     * @param code    TODO.
     * @param content TODO.
     */
    protected ServiceResponse(final ServiceResponseCode code, final Object content) {
        responseCode = code;
        responseContent = content;
    }

    /**
     * TODO.
     *
     * @param code TODO.
     */
    protected ServiceResponse(final ServiceResponseCode code) {
        responseCode = code;
        responseContent = null;
    }

    /**
     * TODO.
     *
     * @param content TODO.
     * @param <T>     TODO.
     * @return TODO.
     */
    @NotNull
    public static <T> ServiceResponseGeneric<T> createSuccessful(final T content) {
        return new ServiceResponseGeneric<>(ServiceResponseCode.Ok, content);
    }

    /**
     * TODO.
     *
     * @return TODO.
     */
    @NotNull
    public static ServiceResponse createSuccessful() {
        return new ServiceResponse(ServiceResponseCode.Ok);
    }

    /**
     * TODO.
     *
     * @param code TODO.
     * @param <T>  TODO.
     * @throws ArgumentException TODO.
     * @return TODO.
     */
    @NotNull
    public static <T> ServiceResponseGeneric<T> createUnsuccessful(final ServiceResponseCode code)
            throws ArgumentException {
        if (code == ServiceResponseCode.Ok) {
            throw new ArgumentException("Invalid code.");
        }

        return new ServiceResponseGeneric<>(code);
    }

    /**
     * TODO.
     *
     * @return TODO.
     */
    @Override
    public ServiceResponseCode getCode() {
        return responseCode;
    }

    /**
     * TODO.
     *
     * @return TODO.
     */
    @Override
    public Object getContent() {
        return responseContent;
    }
}

