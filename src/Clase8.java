import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;

public class Clase8 {

    private static final String USERS_FILE = "users.dat";
    private static final String INDEX_FILE = "index.dat";

    private static final int NUM_BUCKETS = 100;
    private static final long NULL_PTR = -1;

    // [CC | recordOffset | nextOffset]
    private static final int NODE_SIZE = 8 + 8 + 8;

    private static final long BUCKET_AREA_SIZE = (long) NUM_BUCKETS * 8;

    public static void main(String[] args) throws IOException {
        resetFiles();
        initializeIndexFile();
        addUser(1023L, "Usuario1");
        addUser(2057L, "Usuario2");
        addUser(3091L, "Usuario3");
        addUser(4125L, "Usuario4");
        addUser(5189L, "Usuario5");
        addUser(6234L, "Usuario6");
        addUser(7348L, "Usuario7");
        addUser(8452L, "Usuario8");
        addUser(9567L, "Usuario9");
        addUser(10671L, "Usuario10");
        addUser(11785L, "Usuario11");
        addUser(12899L, "Usuario12");
        addUser(13904L, "Usuario13");
        addUser(15018L, "Usuario14");
        addUser(16122L, "Usuario15");
        addUser(17236L, "Usuario16");
        addUser(18340L, "Usuario17");
        addUser(19454L, "Usuario18");
        addUser(20568L, "Usuario19");
        addUser(21672L, "Usuario20");
        addUser(22786L, "Usuario21");
        addUser(23890L, "Usuario22");
        addUser(24904L, "Usuario23");
        addUser(26018L, "Usuario24");
        addUser(27122L, "Usuario25");
        addUser(28236L, "Usuario26");
        addUser(29340L, "Usuario27");
        addUser(30454L, "Usuario28");
        addUser(31568L, "Usuario29");
        addUser(32672L, "Usuario30");
        addUser(33786L, "Usuario31");
        addUser(34890L, "Usuario32");
        addUser(35904L, "Usuario33");
        addUser(37018L, "Usuario34");
        addUser(38122L, "Usuario35");
        addUser(39236L, "Usuario36");
        addUser(40340L, "Usuario37");
        addUser(41454L, "Usuario38");
        addUser(42568L, "Usuario39");
        addUser(43672L, "Usuario40");
        addUser(44786L, "Usuario41");
        addUser(45890L, "Usuario42");
        addUser(46904L, "Usuario43");
        addUser(48018L, "Usuario44");
        addUser(49122L, "Usuario45");
        addUser(50236L, "Usuario46");
        addUser(51340L, "Usuario47");
        addUser(52454L, "Usuario48");
        addUser(53568L, "Usuario49");
        addUser(54672L, "Usuario50");
        addUser(55786L, "Usuario51");
        addUser(56890L, "Usuario52");
        addUser(57904L, "Usuario53");
        addUser(59018L, "Usuario54");
        addUser(60122L, "Usuario55");
        addUser(61236L, "Usuario56");
        addUser(62340L, "Usuario57");
        addUser(63454L, "Usuario58");
        addUser(64568L, "Usuario59");
        addUser(65672L, "Usuario60");
        addUser(66786L, "Usuario61");
        addUser(67890L, "Usuario62");
        addUser(68904L, "Usuario63");
        addUser(70018L, "Usuario64");
        addUser(71122L, "Usuario65");
        addUser(72236L, "Usuario66");
        addUser(73340L, "Usuario67");
        addUser(74454L, "Usuario68");
        addUser(75568L, "Usuario69");
        addUser(76672L, "Usuario70");
        addUser(77786L, "Usuario71");
        addUser(78890L, "Usuario72");
        addUser(79904L, "Usuario73");
        addUser(81018L, "Usuario74");
        addUser(82122L, "Usuario75");
        addUser(83236L, "Usuario76");
        addUser(84340L, "Usuario77");
        addUser(85454L, "Usuario78");
        addUser(86568L, "Usuario79");
        addUser(87672L, "Usuario80");
        addUser(88786L, "Usuario81");
        addUser(89890L, "Usuario82");
        addUser(90904L, "Usuario83");
        addUser(92018L, "Usuario84");
        addUser(93122L, "Usuario85");
        addUser(94236L, "Usuario86");
        addUser(95340L, "Usuario87");
        addUser(96454L, "Usuario88");
        addUser(97568L, "Usuario89");
        addUser(98672L, "Usuario90");
        addUser(99786L, "Usuario91");
        addUser(100890L, "Usuario92");
        addUser(101904L, "Usuario93");
        addUser(103018L, "Usuario94");
        addUser(104122L, "Usuario95");
        addUser(105236L, "Usuario96");
        addUser(106340L, "Usuario97");
        addUser(107454L, "Usuario98");
        addUser(108568L, "Usuario99");
        addUser(109672L, "Usuario100");
        searchWithOutIndex(1023L);
        searchWithIndex(1023L);
    }

    private static int hash(long cc) {
        return (int) (Math.abs(cc) % NUM_BUCKETS);
    }

    private static void resetFiles() throws IOException {
        Files.deleteIfExists(Path.of(USERS_FILE));
        Files.deleteIfExists(Path.of(INDEX_FILE));
    }

    private static void initializeIndexFile() throws IOException {
        try (RandomAccessFile index = new RandomAccessFile(INDEX_FILE, "rw")) {
            for (int i = 0; i < NUM_BUCKETS; i++) {
                index.writeLong(NULL_PTR);
            }
        }
    }

    private static void addUser(long cc, String name) throws IOException {

        long recordOffset;

        try (RandomAccessFile users = new RandomAccessFile(USERS_FILE, "rw")) {
            recordOffset = users.length();
            users.seek(recordOffset);

            users.writeLong(cc);
            users.writeUTF(name);
        }

        int bucket = hash(cc);

        try (RandomAccessFile index = new RandomAccessFile(INDEX_FILE, "rw")) {
            long bucketPos = (long) bucket * 8;

            // Ir a la cabeza del bucket
            index.seek(bucketPos);
            long oldHead = index.readLong();

            // Agregmos el nodo al final
            long newNodeOffset = index.length();
            index.seek(newNodeOffset);

            index.writeLong(cc);
            index.writeLong(recordOffset);
            index.writeLong(oldHead);

            index.seek(bucketPos);
            index.writeLong(newNodeOffset);

            System.out.printf(
                    "Dato insetado: CC: %d | Nombre: %s | Bucket %d | RecordOsset: %d | NodeOffset: %d | oldHead : %d%n",
                    cc, name, bucket, recordOffset, newNodeOffset, oldHead);

        }

    }

    private static void searchWithOutIndex(long ccToFind) throws IOException {
        long start = System.nanoTime();
        int comparisons = 0;
        boolean found = false;

        try (RandomAccessFile users = new RandomAccessFile(USERS_FILE, "r")) {
            while (users.getFilePointer() < users.length()) {
                long cc = users.readLong();
                String name = users.readUTF();
                comparisons++;

                if (cc == ccToFind) {
                    found = true;
                    System.out.println("Lo encontramos!!!!!!");
                    System.out.println("CC: " + cc);
                    System.out.println("Nombre: " + name);
                    System.out.println("Comparaciones: " + comparisons);
                    long end = System.nanoTime();
                    printTime(start, end);
                    break;
                }

            }
        }

        if (!found) {
            System.out.println("No pudimos encontrar el dato");
            System.out.println("Comparaciones: " + comparisons);
            long end = System.nanoTime();
            printTime(start, end);
        }

    }

    private static void searchWithIndex(long ccToFind) throws IOException {
        long start = System.nanoTime();
        int comparisons = 0;

        int bucket = hash(ccToFind);

        try (RandomAccessFile index = new RandomAccessFile(INDEX_FILE, "r");
                RandomAccessFile users = new RandomAccessFile(USERS_FILE, "r")) {

            long bucketPos = (long) bucket * 8;
            index.seek(bucketPos);

            long currentNodeOffset = index.readLong();

            while (currentNodeOffset != NULL_PTR) {
                index.seek(currentNodeOffset);

                long cc = index.readLong();
                long recordOffset = index.readLong();
                long nextOffset = index.readLong();

                comparisons++;

                if (ccToFind == cc) {
                    users.seek(recordOffset);
                    long foundCc = users.readLong();
                    String foundName = users.readUTF();
                    System.out.println("Lo encontramos usando indice!!!!!!");
                    System.out.println("CC: " + foundCc);
                    System.out.println("Nombre: " + foundName);
                    System.out.println("Comparaciones: " + comparisons);

                    long end = System.nanoTime();

                    printTime(start, end);
                    return;
                }

                currentNodeOffset = nextOffset;

            }

            System.out.println("No pudimos encontrar el dato usando indice");
            System.out.println("Comparaciones: " + comparisons);
            long end = System.nanoTime();
            printTime(start, end);

        }

    }

    private static void printTime(long start, long end) {
        long nanos = end - start;
        double millis = nanos / 1_000_000.0;

        System.out.println("Tiempo: " + nanos + " ns");
        System.out.println("Tiempo: " + millis + " ms");
    }

    private static void printBucketUsed() throws IOException {
        // TODO: Implementar
    }

    private static void printIndexNodes() throws IOException {
        // TODO: Implementar
    }

}
