package by.bsuir.MY.domain;

import by.bsuir.MY.domain.Interf.ServiceResponseCode;
import by.bsuir.MY.domain.Interf.ServiceResponseMessage;
import by.bsuir.MY.domain.exception.ArgumentException;
import org.jetbrains.annotations.NotNull;

/**
 * .
 */
public class ServiceResponse
        implements by.bsuir.MY.domain.Interf.ServiceResponse {
    /**
     * .
     */
    private ServiceResponseCode responseCode;
    /**
     * .
     */
    private Object responseContent;

    /**
     * .
     *
     * @param code    .
     * @param content .
     */
    protected ServiceResponse(final ServiceResponseCode code, final Object content) {
        responseCode = code;
        responseContent = content;
    }

    /**
     * .
     *
     * @param code .
     */
    protected ServiceResponse(final ServiceResponseCode code) {
        responseCode = code;
        responseContent = null;
    }

    /**
     * .
     *
     * @param content .
     * @param <T>     .
     * @return .
     */
    @NotNull
    public static <T> ServiceResponseGeneric<T> createSuccessful(final T content) {
        return new ServiceResponseGeneric<>(ServiceResponseCode.Ok, content);
    }

    /**
     * .
     *
     * @return .
     */
    @NotNull
    public static ServiceResponse createSuccessful() {
        return new ServiceResponse(
                ServiceResponseCode.Success,
                ServiceResponseMessage.getMessage(ServiceResponseCode.Success)
        );
    }

    /**
     * .
     *
     * @param code .
     * @param <T>  .
     * @throws ArgumentException .
     * @return .
     */
    @NotNull
    public static ServiceResponseGeneric<String> createUnsuccessful(
            final ServiceResponseCode code
    )
    {
        if (code == ServiceResponseCode.Ok || code == null) {
            throw new ArgumentException("Invalid code.");
        }

        return new ServiceResponseGeneric<>(code, ServiceResponseMessage.getMessage(code));
    }

    /**
     * .
     *
     * @return .
     */
    @Override
    public ServiceResponseCode getCode() {
        return responseCode;
    }

    /**
     * .
     *
     * @return .
     */
    @Override
    public Object getContent() {
        return responseContent;
    }
}

