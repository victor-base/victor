public class Victor implements AutoCloseable{

    static {
        System.loadLibrary("victorjni");
    }

    private long nativePtr;

    public Victor(int type, int method, int dims) {
        this.nativePtr = allocIndex(type, method, dims);
        if (this.nativePtr == 0) {
            throw new RuntimeException("Unable to create index.");
        }
    }

    public int insert(long id, float[] vector, int dims) {
        if (this.nativePtr == 0)
            throw new IllegalStateException("Index has been destroyed.");

        int code = nativeInsert(nativePtr, id, vector, dims);
        VictorError.check(code, "insert()");
        return code;
    }

    public int delete(long id) {
        if (this.nativePtr == 0)
            throw new IllegalStateException("Index has been destroyed.");
        return nativeDelete(this.nativePtr, id);
    }


    @Override
    public void close() {
        if (this.nativePtr == 0) 
            throw new IllegalStateException("Index has been destroyed.");
        int code = nativeDestroy(this.nativePtr);
        VictorError.check(code, "close()");
        this.nativePtr = 0;
    }

    public boolean isDestroyed() {
        return this.nativePtr == 0;
    }

    // Métodos nativos privados
    private static native long allocIndex(int type, int method, int dims);
    private static native int nativeInsert(long ptr, long id, float[] vector, int dims);
    private static native int nativeDelete(long ptr, long id);
    private static native long nativeDestroy(long ptr);
}
