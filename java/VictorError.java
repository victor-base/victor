public class VictorError {

    public static final int SUCCESS             = 0;
    public static final int INVALID_INIT        = 1;
    public static final int INVALID_INDEX       = 2;
    public static final int INVALID_VECTOR      = 3;
    public static final int INVALID_RESULT      = 4;
    public static final int INVALID_DIMENSIONS  = 5;
    public static final int INVALID_ID          = 6;
    public static final int INDEX_EMPTY         = 7;
    public static final int SYSTEM_ERROR        = 8;

    public static void check(int code, String context) {
        switch (code) {
            case SUCCESS:
                return;

            case INVALID_INIT:
                throw new IllegalStateException(context + ": Invalid initialization.");

            case INVALID_INDEX:
                throw new IllegalArgumentException(context + ": Invalid index reference.");

            case INVALID_VECTOR:
                throw new IllegalArgumentException(context + ": Invalid or null vector.");

            case INVALID_RESULT:
                throw new IllegalStateException(context + ": Invalid result structure.");

            case INVALID_DIMENSIONS:
                throw new IllegalArgumentException(context + ": Dimension mismatch.");

            case INVALID_ID:
                throw new IllegalArgumentException(context + ": Invalid ID provided.");

            case INDEX_EMPTY:
                throw new IllegalStateException(context + ": Index is empty.");

            case SYSTEM_ERROR:
                throw new VictorException(context + ": System error or internal failure.");

            default:
                throw new VictorException(context + ": Unknown error code " + code);
        }
    }
}
