package by.bsuir.MY.domain.Interf;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO.
 */
public final class ServiceResponseMessage {

    /**
     * TODO.
     */
    private static Map<ServiceResponseCode, String> messages = new HashMap<>();

    static{
        messages.put(ServiceResponseCode.Ok, "Ok");
        messages.put(ServiceResponseCode.BadRequest, "Bad request");
    }

    /**
     * TODO.
     */
    private ServiceResponseMessage() {
    }



    /**
     * TODO.
     *
     * @param code TODO.
     * @return TODO.
     */
    public static String getMessage(final ServiceResponseCode code) {
        return messages.get(code);
    }
}
