package exception;

public enum VictorError {
    SUCCESS(0, ""),
    INVALID_INIT(1, "Invalid initialization."),
    INVALID_INDEX(2, "Invalid index reference."),
    INVALID_VECTOR(3, "Invalid or null vector."),
    INVALID_RESULT(4, "Invalid result structure."),
    INVALID_DIMENSIONS(5, "Dimension mismatch."),
    INVALID_ID(6, "Invalid ID provided."),
    INDEX_EMPTY(7, "Index is empty."),
    SYSTEM_ERROR(8, "System error or internal failure.");

    private final int code;
    private final String message;

    VictorError(int i, String message) {
        this.message = message;
        this.code = i;
    }

    public int getCode() {
        return code;
    }
    public String getMessage(String context) {
        return context + ": " + this.message;
    }

    public static VictorError fromCode(int code) {
        for (VictorError error : VictorError.values()) {
            if (error.getCode() == code) {
                return error;
            }
        }
        return VictorError.SYSTEM_ERROR; // Default case
    }

    public static void check(int code, String context) {
        VictorError error = fromCode(code);
        switch (error) {
            case SUCCESS:
                return;
            case INVALID_INIT:
            case INVALID_RESULT:
            case INDEX_EMPTY:
                throw new IllegalStateException(error.getMessage(context));
            case INVALID_INDEX:
            case INVALID_VECTOR:
            case INVALID_DIMENSIONS:
            case INVALID_ID:
                throw new IllegalArgumentException(error.getMessage(context));
            case SYSTEM_ERROR:
                throw new VictorException(error.getMessage(context));
        }
    }
}