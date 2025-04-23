package models;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public class MatchResult extends Structure {
    public long id;
    public long distance;

    public MatchResult() {
        super();
    }

    public MatchResult(Pointer p) {
        super(p);
        read();
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("id", "distance");
    }
}