package by.bsuir.MY.domain.Interf;

import java.util.HashMap;
import java.util.Map;

/**
 * .
 */
public final class ServiceResponseMessage {

    /**
     * .
     */
    private static Map<ServiceResponseCode, String> messages = new HashMap<>();

    static{
        messages.put(ServiceResponseCode.Success, "Success");
        messages.put(ServiceResponseCode.BadRequest, "Bad request");
        messages.put(ServiceResponseCode.WrongParameters, "Wrong parameters");
        messages.put(ServiceResponseCode.EntityNotFount, "Entity not found");
        messages.put(ServiceResponseCode.AccessDenied, "Access denied");
        messages.put(ServiceResponseCode.XMLFail, "Wrong XML data");
    }

    /**
     * .
     */
    private ServiceResponseMessage() {
    }



    /**
     * .
     *
     * @param code .
     * @return .
     */
    public static String getMessage(final ServiceResponseCode code) {
        return messages.get(code);
    }
}
