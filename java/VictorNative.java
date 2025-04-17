public abstract class VictorNative implements AutoCloseable {

    static {
        System.loadLibrary("victorjni");
    }

    protected static native long allocIndex(int type, int method, int dims);
    protected static native int nativeInsert(long ptr, long id, float[] vector, int dims);
    protected static native int nativeDelete(long ptr, long id);
    protected static native long nativeDestroy(long ptr);
}