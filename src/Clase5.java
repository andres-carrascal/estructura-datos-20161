import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Clase5 {

    static int funcHash(String key, int m) {
        return Math.abs(key.hashCode()) % m;
    }

    public static void main(String[] args) {

        String[] nombres = { "Ana", "Pedro", "Jose", "Luis", "Luz", "Carlos", "Lucas", "Jhon", "Oscar", "Laura" };

        int m = nombres.length;

        List<List<String>> tabla = new ArrayList<>(m);

        // Paso 1: Rellenar los buckets con las listas ligadas
        for (int i = 0; i < m; i++) {
            tabla.add(new LinkedList<>());
        }

        // Paso 2: Agregar la información a los buckets
        for (String nombre : nombres) {
            int index = funcHash(nombre, m);
            tabla.get(index).add(nombre);
        }

        // Paso 3: Imrpimir la información
        for (int i = 0; i < m; i++) {
            System.out.println(i + " -------- >" + tabla.get(i));
        }

    }
}
