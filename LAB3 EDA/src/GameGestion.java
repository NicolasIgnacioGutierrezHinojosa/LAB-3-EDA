import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;

// Clase
class Game {
    private String name;
    private String category;
    private int price;
    private int quality;

    // Constructor
    public Game(String name, String category, int price, int quality) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quality = quality;
    }

    // Getters
    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getPrice() { return price; }
    public int getQuality() { return quality; }

    @Override
    public String toString() {
        return String.format("Game{name='%s', category='%s', price=%d, quality=%d}",
                name, category, price, quality);
    }
}

// Clase de los dataset
class Dataset {
    private List<Game> data;
    private String sortedByAttribute;

    public Dataset(List<Game> data) {
        this.data = new ArrayList<>(data);
        this.sortedByAttribute = "";
    }

    public List<Game> getGamesByPrice(int price) {
        if ("price".equals(sortedByAttribute)) return binarySearchByPrice(price);
        return linearSearch(g -> g.getPrice() == price);
    }

    public List<Game> getGamesByPriceRange(int low, int high) {
        if ("price".equals(sortedByAttribute)) return rangeSearchByPrice(low, high);
        return linearSearch(g -> g.getPrice() >= low && g.getPrice() <= high);
    }

    public List<Game> getGamesByCategory(String category) {
        if ("category".equals(sortedByAttribute)) return binarySearchByCategory(category);
        return linearSearch(g -> g.getCategory().equalsIgnoreCase(category));
    }

    public List<Game> getGamesByQuality(int quality) {
        if ("quality".equals(sortedByAttribute)) return binarySearchByQuality(quality);
        return linearSearch(g -> g.getQuality() == quality);
    }

    public void sortByAlgorithm(String algorithm, String attribute) {
        Comparator<Game> comp;
        switch (attribute.toLowerCase()) {
            case "category": comp = Comparator.comparing(Game::getCategory); break;
            case "quality":  comp = Comparator.comparingInt(Game::getQuality); break;
            case "price":
            default:          comp = Comparator.comparingInt(Game::getPrice); attribute = "price";
        }
        List<Game> copy = new ArrayList<>(data);
        switch (algorithm.toLowerCase()) {
            case "bubblesort":    bubbleSort(copy, comp); break;
            case "insertionsort": insertionSort(copy, comp); break;
            case "selectionsort": selectionSort(copy, comp); break;
            case "mergesort":     mergeSort(copy, comp); break;
            case "quicksort":     quickSort(copy, comp, 0, copy.size() - 1); break;
            default:               Collections.sort(copy, comp);algorithm = "Collections.sort"; break;
        }
        data = copy;
        sortedByAttribute = attribute;
        System.out.println("Ordenado con: " + algorithm);
        System.out.println("Ordenada por: " + attribute);
    }

    public List<Game> getData() {
        return Collections.unmodifiableList(data);
    }

    // Búsqueda y auxiliares
    private List<Game> linearSearch(Predicate<Game> pred) {
        List<Game> res = new ArrayList<>();
        for (Game g : data) if (pred.test(g)) res.add(g);
        return res;
    }

    private List<Game> binarySearchByPrice(int price) {
        return linearSearch(g -> g.getPrice() == price);
    }
    private List<Game> rangeSearchByPrice(int low, int high) {
        return linearSearch(g -> g.getPrice() >= low && g.getPrice() <= high);
    }
    private List<Game> binarySearchByCategory(String cat) {
        return linearSearch(g -> g.getCategory().equalsIgnoreCase(cat));
    }
    private List<Game> binarySearchByQuality(int quality) {
        return linearSearch(g -> g.getQuality() == quality);
    }

    // Ordenamientos o sort hubo una talla con esta wea que después te cuento jsjsjsjs
    private void bubbleSort(List<Game> list, Comparator<Game> comp) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - 1 - i; j++)
                if (comp.compare(list.get(j), list.get(j + 1)) > 0)
                    Collections.swap(list, j, j + 1);
    }

    private void insertionSort(List<Game> list, Comparator<Game> comp) {
        for (int i = 1; i < list.size(); i++) {
            Game key = list.get(i);
            int j = i - 1;
            while (j >= 0 && comp.compare(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    private void selectionSort(List<Game> list, Comparator<Game> comp) {
        for (int i = 0; i < list.size() - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < list.size(); j++)
                if (comp.compare(list.get(j), list.get(minIdx)) < 0)
                    minIdx = j;
            Collections.swap(list, i, minIdx);
        }
    }

    private void mergeSort(List<Game> list, Comparator<Game> comp) {
        if (list.size() < 2) return;
        int mid = list.size() / 2;
        List<Game> left = new ArrayList<>(list.subList(0, mid));
        List<Game> right = new ArrayList<>(list.subList(mid, list.size()));
        mergeSort(left, comp);
        mergeSort(right, comp);
        merge(list, left, right, comp);
    }

    private void merge(List<Game> list, List<Game> left, List<Game> right, Comparator<Game> comp) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (comp.compare(left.get(i), right.get(j)) <= 0)
                list.set(k++, left.get(i++));
            else
                list.set(k++, right.get(j++));
        }
        while (i < left.size()) list.set(k++, left.get(i++));
        while (j < right.size()) list.set(k++, right.get(j++));
    }

    private void quickSort(List<Game> list, Comparator<Game> comp, int low, int high) {
        if (low < high) {
            int p = partition(list, comp, low, high);
            quickSort(list, comp, low, p - 1);
            quickSort(list, comp, p + 1, high);
        }
    }

    private int partition(List<Game> list, Comparator<Game> comp, int low, int high) {
        Game pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comp.compare(list.get(j), pivot) <= 0) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return i + 1;
    }
}

class DatasetLoader {

    public static ArrayList<Game> cargarDesdeCSV(String rutaArchivo) {
        ArrayList<Game> listaGames = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;  // si tienes encabezados, saltarlos
                    continue;
                }
                String[] partes = linea.split(",");
                if (partes.length >= 4) {
                    String nombre = partes[0];
                    String categoria = partes[1];
                    int precio = Integer.parseInt(partes[2]);
                    int calidad = Integer.parseInt(partes[3]);

                    Game game = new Game(nombre, categoria, precio, calidad);
                    listaGames.add(game);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }
        return listaGames;
    }
}

// Generación aleatoria y CSV
class GenerateData {
    private static final String[] NAMES = {"Dragon","Empire","Quest","Galaxy","Legends","Warrior"};
    private static final String[] CATEGORIES = {"Acción","Aventura","Estrategia","RPG","Deportes","Simulación"};
    private static final Random RAND = new Random();

    public static List<Game> generate(int N) {
        List<Game> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            String name = NAMES[RAND.nextInt(NAMES.length)] + NAMES[RAND.nextInt(NAMES.length)];
            String cat  = CATEGORIES[RAND.nextInt(CATEGORIES.length)];
            int price    = RAND.nextInt(70001);
            int quality  = RAND.nextInt(101);
            list.add(new Game(name, cat, price, quality));
        }
        return list;
    }

    public static void toCSV(List<Game> sample, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("Name,Category,Price,Quality\n");
            for (Game g : sample) {
                writer.append(g.getName()).append(",")
                        .append(g.getCategory()).append(",")
                        .append(String.valueOf(g.getPrice())).append(",")
                        .append(String.valueOf(g.getQuality())).append("\n");
            }
            System.out.println("CSV guardado en: " + fileName);
        } catch (IOException e) {
            System.err.println("Error al guardar CSV: " + e.getMessage());
        }
    }

    public static String elegirArchivoCSV() {
        File dir = new File(".");
        File[] archivos = dir.listFiles((d, name) -> name.endsWith(".csv") && name.startsWith("game_"));

        if (archivos == null || archivos.length == 0) {
            System.out.println("No hay archivos CSV disponibles.");
            return null;
        }

        System.out.println("Archivos disponibles:");
        for (int i = 0; i < archivos.length; i++) {
            System.out.println((i + 1) + ". " + archivos[i].getName());
        }

        Scanner scanner = new Scanner(System.in);
        int eleccion = -1;
        while (eleccion < 1 || eleccion > archivos.length) {
            System.out.print("Seleccione un archivo (1-" + archivos.length + "): ");
            if (scanner.hasNextInt()) {
                eleccion = scanner.nextInt();
            } else {
                System.out.println("Entrada inválida. Intente de nuevo.");
                scanner.next(); // descartar entrada no numérica
            }
        }


        return archivos[eleccion - 1].getName();
    }
}

// Aplicación principal con menú interactivo
class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Dataset ds = null;
        while (true) {
            System.out.println("\n=== Menú ===");
            System.out.println("1. Generar Datos de Dataset");
            System.out.println("2. Cargar archivo existente");
            System.out.println("3. Mostrar Dataset");
            System.out.println("4. Obtener juegos de precio específico");
            System.out.println("5. Obtener juegos por rango de precios");
            System.out.println("6. Obtener juegos por categoría");
            System.out.println("7. Obtener juegos por calidad");
            System.out.println("8. Ordenar Dataset");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            int opcion = sc.nextInt(); sc.nextLine();

            switch (opcion) {
                case 1 -> {
                    System.out.print("Número de juegos a generar: ");
                    int N = sc.nextInt(); sc.nextLine();
                    List<Game> data = GenerateData.generate(N);
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String fileName = "game_" + timeStamp + ".csv";
                    GenerateData.toCSV(data, fileName);
                    ds = new Dataset(data);
                }
                case 2 -> {
                    String archivo = GenerateData.elegirArchivoCSV();
                    if (archivo != null) {
                        System.out.println("Archivo seleccionado: " + archivo);
                    }
                    String rutaArchivo = "./" + archivo;
                    ArrayList<Game> data = DatasetLoader.cargarDesdeCSV(rutaArchivo);
                    ds = new Dataset(data);
                }
                case 3 -> {
                    if (ds == null) System.out.println("Primero genere un dataset (opción 1).");
                    else ds.getData().forEach(System.out::println);
                }
                case 4 -> {
                    if (ds == null) { System.out.println("Dataset vacío."); break; }
                    System.out.print("Precio exacto: ");
                    int p = sc.nextInt(); sc.nextLine();
                    long inicio = System.nanoTime();
                    printList(ds.getGamesByPrice(p));
                    long fin = System.nanoTime();
                    System.out.println("Tiempo transcurrido: " + (fin - inicio) / 1_000_000 + " ms");
                }
                case 5 -> {
                    if (ds == null) { System.out.println("Dataset vacío."); break; }
                    System.out.print("Precio mínimo: "); int low = sc.nextInt(); sc.nextLine();
                    System.out.print("Precio máximo: "); int high = sc.nextInt(); sc.nextLine();
                    long inicio = System.nanoTime();
                    printList(ds.getGamesByPriceRange(low, high));
                    long fin = System.nanoTime();
                    System.out.println("Tiempo transcurrido: " + (fin - inicio) / 1_000_000 + " ms");
                }
                case 6 -> {
                    if (ds == null) { System.out.println("Dataset vacío."); break; }
                    System.out.print("Categoría: ");
                    long inicio = System.nanoTime();
                    printList(ds.getGamesByCategory(sc.nextLine()));
                    long fin = System.nanoTime();
                    System.out.println("Tiempo transcurrido: " + (fin - inicio) / 1_000_000 + " ms");
                }
                case 7 -> {
                    if (ds == null) { System.out.println("Dataset vacío."); break; }
                    System.out.print("Calidad: "); int q = sc.nextInt(); sc.nextLine();
                    long inicio = System.nanoTime();
                    printList(ds.getGamesByQuality(q));
                    long fin = System.nanoTime();
                    System.out.println("Tiempo transcurrido: " + (fin - inicio) / 1_000_000 + " ms");
                }
                case 8 -> {
                    if (ds == null) { System.out.println("Dataset vacío."); break; }
                    System.out.print("Algoritmo (bubbleSort, insertionSort, selectionSort, mergeSort, quickSort, default): ");
                    String alg = sc.nextLine();
                    System.out.print("Atributo (price, category, quality): ");
                    String attr = sc.nextLine();
                    long inicio = System.nanoTime();
                    ds.sortByAlgorithm(alg, attr);
                    long fin = System.nanoTime();
                    System.out.println("Dataset ordenado.");
                    System.out.println("Tiempo transcurrido: " + (fin - inicio) / 1_000_000 + " ms");
                }
                case 0 -> {
                    System.out.println("Saliendo...");
                    sc.close();
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private static void printList(List<Game> list) {
        if (list == null || list.isEmpty()) System.out.println("No se encontraron juegos.");
        else list.forEach(System.out::println);
    }
}
