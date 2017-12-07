package by.bsuir.lab03.domain;

public enum UserRole {
    GUEST,
    CLIENT,
    ADMIN;

    private static final String GUEST_ROLE_STRING = "GUEST";
    private static final String CLIENT_ROLE_STRING = "CLIENT";
    private static final String ADMIN_ROLE_STRING = "ADMIN";

    public static String roleToString(UserRole role) {
        switch (role) {
            case GUEST:
                return GUEST_ROLE_STRING;
            case CLIENT:
                return CLIENT_ROLE_STRING;
            case ADMIN:
                return ADMIN_ROLE_STRING;
            default:
                return GUEST_ROLE_STRING;
        }
    }

    public static UserRole stringToRole(String role) {
        switch (role) {
            case GUEST_ROLE_STRING:
                return GUEST;
            case CLIENT_ROLE_STRING:
                return CLIENT;
            case ADMIN_ROLE_STRING:
                return ADMIN;
            default:
                return GUEST;
        }
    }
}
