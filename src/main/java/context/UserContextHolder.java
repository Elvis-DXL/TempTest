package context;


import java.util.Optional;

public final class UserContextHolder {
    private static final String CONTEXT_KEY = "user-context";

    public static void clear() {
        ContextHolder.remove(CONTEXT_KEY);
    }

    public static void setUser(UserContext userContext) {
        ContextHolder.set(CONTEXT_KEY, userContext);
    }

    public static Optional<UserContext> getUser() {
        return Optional.ofNullable((UserContext) ContextHolder.get(CONTEXT_KEY));
    }
}