public class Clase4 {

    static int funcHash(String key, int m) {
        return key.hashCode() % m;
    }

    public static void main(String[] args) throws Exception {
        int m = 10; // Tabla

        String[] nombres = { "Ana", "Pedro", "Jose", "Luis", "Luz", "Carlos", "Lucas", "Jhon", "Oscar", "Laura" };

        for (String nombre : nombres) {
            int indice = funcHash(nombre, m);
            System.out.println(nombre + " -> Indice: " + indice);
        }

    }

}
