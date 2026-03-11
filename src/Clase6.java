
public class Clase6 {
    public static void main(String[] args) {

        String nombre = "🚀🎶"; // ---> UTF-16
        int hash = 0;

        System.out.println("Calculando hash de: " + nombre);

        for (int i = 0; i < nombre.length(); i++) {
            char c = nombre.charAt(i); // 0 ------- 65535
            int valor = (int) c;

            System.out.println("Carácter: '" + c + "' -> valor Unicode: " + valor);
            hash = 31 * hash + c;
            System.out.println("Hash parcial: " + hash);
        }

        int m = 100;
        int index = Math.floorMod(hash, m);

        System.out.println("Hash final calculado manualmente: " + hash);
        System.out.println("Hash real usando hashCode():     " + nombre.hashCode());
        System.out.println("Para una tabla de longitud " + m + " ocupa el índice "+ index);
    }
}
