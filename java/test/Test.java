package test;
import exception.VictorException;
import victor.Victor;
import models.MatchResult;

public class Test {

    public static void run() {
        try (Victor victor = new Victor(0, 1, 128, 0)) {
            // Insert vectors in index
            int id1 = 1;
            float[] vector1 = new float[128];
            for (int i = 0; i < vector1.length; i++) {
                vector1[i] = (float) i / vector1.length;
            }

            victor.insert(id1, vector1, 128);
            System.out.println("\nVector con ID " + id1 + " insertado.\n");

            MatchResult result = victor.search(vector1, 128);
            System.out.println("\nResultado de búsqueda: ID =" + result.id + ", Distancia = " + result.distance + "\n");

            victor.delete(id1);
            System.out.println("\nVector con ID " + id1 + " borrado.\n");
        } catch (Exception ex) {
            throw new VictorException(ex.getMessage());
        }
    }
}
