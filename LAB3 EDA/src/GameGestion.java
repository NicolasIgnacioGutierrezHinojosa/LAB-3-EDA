import java.io.*;
import java.util.*;

public class GameGestion {
    public class Game {
        String name;
        String category;
        int price;
        int quality;

        Game(String name, String category, int price, int quatity) {
            this.name = name;
            this.category = category;
            this.price = price;
            this.quality = quatity;
        }
    }

    public class Dataset {
        ArrayList<Game> data;
        String sortedByAttribute;

        Dataset(ArrayList<Game> data) {
            this.data = data;
        }

        ArrayList<Game> getGamesByPrice(int price) {
            ArrayList<Game> gamesPrice = new ArrayList<>();
            int start = 0;
            int end = data.size() - 1;
            int StartCounter = 0;

            if (sortedByAttribute.equals("price")) {
                while (start <= end) {
                    int middle = (start + end) / 2;
                    if (data.get(middle).price == price && (middle == 0 || data.get(middle - 1).price != price)) {
                        StartCounter = middle;
                        break;
                    } else if (data.get(middle).price > price) {
                        end = middle - 1;
                    } else {
                        start = middle + 1;
                    }
                }
                for (int i = StartCounter; i < data.size(); i++) {
                    if (data.get(i).price == price) {
                        gamesPrice.add(data.get(i));
                    }
                }
            } else {
                for (Game g : data) {
                    if (g.price == price) {
                        gamesPrice.add(g);
                    }
                }
            }
            return gamesPrice;
        }

        ArrayList<Game> getGamesByPriceRange(int lowerPrice, int higherPrice) {
            ArrayList<Game> gamesRangePrice = new ArrayList<>();
            int start = 0;
            int end = data.size() - 1;
            int StartCounter = 0;
            if (sortedByAttribute.equals("price")) {
                while (start <= end) {
                    int middle = (start + end) / 2;
                    if (data.get(middle).price == lowerPrice && (middle == 0 || data.get(middle - 1).price != lowerPrice)) {
                        StartCounter = middle;
                        break;
                    } else if (data.get(middle).price > lowerPrice) {
                        end = middle - 1;
                    } else {
                        start = middle + 1;
                    }
                }
                for (int i = StartCounter; i < data.size(); i++) {
                    if (data.get(i).price >= lowerPrice && data.get(i).price <= higherPrice) {
                        gamesRangePrice.add(data.get(i));
                    }
                }
            } else {
                for (Game g : data) {
                    if (g.price <= lowerPrice && g.price >= higherPrice) {
                        gamesRangePrice.add(g);
                    }
                }
            }
            return gamesRangePrice;
        }

        ArrayList<Game> getGamesByCategory(String category) {
            ArrayList<Game> gamesCategory = new ArrayList<>();
            int start = 0;
            int end = data.size() - 1;
            int StartCounter = 0;
            if (sortedByAttribute.equals("category")) {
                while (start <= end) {
                    int middle = (start + end) / 2;
                    if (data.get(middle).category.equals(category) && (middle == 0 || !data.get(middle - 1).category.equals(category))) {
                        StartCounter = middle;
                        break;
                    } else if (data.get(middle).category.equals(category)) {
                        end = middle - 1;
                    } else {
                        start = middle + 1;
                    }
                }
                for (int i = StartCounter; i < data.size(); i++) {
                    if (data.get(i).category.equals(category)) {
                        gamesCategory.add(data.get(i));
                    } else break;
                }
            } else {
                for (Game g : data) {
                    if (g.category.equals(category)) {
                        gamesCategory.add(g);
                    }
                }
            }
            return gamesCategory;
        }

        ArrayList<Game> getGamesByQuality(int quality) {
            ArrayList<Game> gamesQuality = new ArrayList<>();
            int start = 0;
            int end = data.size() - 1;
            int StartCounter = 0;
            if (sortedByAttribute.equals("quality")) {
                while (start <= end) {
                    int middle = (start + end) / 2;
                    if (data.get(middle).quality == quality && (middle == 0 || data.get(middle - 1).quality != quality)) {
                        StartCounter = middle;
                        break;
                    } else if (data.get(middle).quality > quality) {
                        end = middle - 1;
                    } else {
                        start = middle + 1;
                    }
                }
                for (int i = StartCounter; i < data.size(); i++) {
                    if (data.get(i).quality == quality) {
                        gamesQuality.add(data.get(i));
                    } else break;
                }
            } else {
                for (Game g : data) {
                    if (g.quality == quality) {
                        gamesQuality.add(g);
                    }
                }
            }
            return gamesQuality;
        }

        private void mergeSort(ArrayList<Game> data, String attribute) {
            if (data.size() <= 1) return;

            int mid = data.size() / 2;
            ArrayList<Game> leftGames = new ArrayList<>(data.subList(0, mid));
            ArrayList<Game> rightGames = new ArrayList<>(data.subList(mid, data.size()));

            mergeSort(leftGames, attribute);
            mergeSort(rightGames, attribute);

            merge(data, leftGames, rightGames, attribute);
        }

        private void merge(ArrayList<Game> data, ArrayList<Game> leftGames, ArrayList<Game> rightGames, String attribute) {
            int i = 0, j = 0, k = 0;

            while (i < leftGames.size() && j < rightGames.size()) {
                boolean left;
                if (!attribute.equals("category") && !attribute.equals("quality")) {
                    left = leftGames.get(i).price <= rightGames.get(j).price;
                } else if (attribute.equals("category")) {
                    left = leftGames.get(i).category.compareTo(rightGames.get(j).category) <= 0;
                } else {
                    left = leftGames.get(i).quality <= rightGames.get(j).quality;
                }

                if (left) {
                    data.set(k++, leftGames.get(i++));
                } else {
                    data.set(k++, rightGames.get(j++));
                }
            }
            while (i < leftGames.size()) {
                data.set(k++, leftGames.get(i++));
            }
            while (j < rightGames.size()) {
                data.set(k++, rightGames.get(j++));
            }
        }

        private void quickSort(ArrayList<Game> data, int low, int high, String attribute) {
            if (low < high) {
                int pivot = partition(data, low, high, attribute);
                quickSort(data, low, pivot - 1, attribute);
                quickSort(data, pivot + 1, high, attribute);
            }
        }

        private int partition(ArrayList<Game> data, int low, int high, String attribute) {
            Game pivot = data.get(high);
            int i = low - 1;

            for (int j = low; j < high; j++) {
                boolean condition;

                if (!attribute.equals("category") && !attribute.equals("quality")) {
                    condition = data.get(j).price <= pivot.price;
                } else if (attribute.equals("category")) {
                    condition = data.get(j).category.compareTo(pivot.category) <= 0;
                } else {
                    condition = data.get(j).quality <= pivot.quality;
                }

                if (condition) {
                    i++;
                    Collections.swap(data, i, j);
                }
            }
            Collections.swap(data, i + 1, high);
            return i + 1;
        }

        void sortByAlgorithm(String algorithm, String attribute) {

            if (algorithm.equals("bubbleSort")) { //BubbleSort selector
                if (!attribute.equals("category") && !attribute.equals("quality")) { //SortPrice
                    for (int i = 0; i < data.size() - 1; i++) {
                        for (int j = 0; j < data.size() - i - 1; j++) {
                            if (data.get(j).price > data.get(j + 1).price) {
                                Collections.swap(data, j, j + 1);
                            }
                        }
                    }
                    this.sortedByAttribute = "price";
                } else if (attribute.equals("category")) { //SortCategory
                    for (int i = 0; i < data.size() - 1; i++) {
                        for (int j = 0; j < data.size() - i - 1; j++) {
                            if (data.get(j).category.compareTo(data.get(j + 1).category) > 0) {
                                Collections.swap(data, j, j + 1);
                            }
                        }
                    }

                    this.sortedByAttribute = "category";
                } else { //SortQuality
                    for (int i = 0; i < data.size() - 1; i++) {
                        for (int j = 0; j < data.size() - i - 1; j++) {
                            if (data.get(j).quality > data.get(j + 1).quality) {
                                Collections.swap(data, j, j + 1);
                            }
                        }
                        this.sortedByAttribute = "quality";
                    }
                }
            } else if (algorithm.equals("insertionSort")) { //insertionSort selector
                if (!attribute.equals("category") && !attribute.equals("quality")) { //SortPrice
                    for (int i = 1; i < data.size(); i++) {
                        int j = i;
                        while (j > 0 && data.get(j - 1).price > data.get(j).price) {
                            Collections.swap(data, j, j - 1);
                            j--;
                        }
                    }
                    this.sortedByAttribute = "price";
                } else if (attribute.equals("category")) { //SortCategory
                    for (int i = 1; i < data.size(); i++) {
                        int j = i;
                        while (j > 0 && data.get(j - 1).category.compareTo(data.get(j).category) > 0) {
                            Collections.swap(data, j, j - 1);
                            j--;
                        }
                    }
                    this.sortedByAttribute = "category";
                } else { //SortQuality
                    for (int i = 1; i < data.size(); i++) {
                        int j = i;
                        while (j > 0 && data.get(j - 1).quality > data.get(j).quality) {
                            Collections.swap(data, j, j - 1);
                            j--;
                        }
                    }
                    this.sortedByAttribute = "quality";
                }
            } else if (algorithm.equals("selectionSort")) { //selectionSort selector
                if (!attribute.equals("category") && !attribute.equals("quality")) { //SortPrice
                    for (int i = 0; i < data.size() - 1; i++) {
                        int min = i;
                        for (int j = i + 1; j < data.size(); j++) {
                            if (data.get(j).price < data.get(min).price) {
                                min = j;
                            }
                        }
                        Collections.swap(data, i, min);
                    }
                    this.sortedByAttribute = "price";
                } else if (attribute.equals("category")) { //SortCategory
                    for (int i = 0; i < data.size() - 1; i++) {
                        int min = i;
                        for (int j = i + 1; j < data.size(); j++) {
                            if (data.get(j).category.compareTo(data.get(min).category) < 0) {
                                min = j;
                            }
                        }
                        Collections.swap(data, i, min);
                    }
                    this.sortedByAttribute = "category";
                } else { //SortQuality
                    for (int i = 0; i < data.size() - 1; i++) {
                        int min = i;
                        for (int j = i + 1; j < data.size(); j++) {
                            if (data.get(j).quality < data.get(min).quality) {
                                min = j;
                            }
                        }
                        Collections.swap(data, i, min);
                    }
                    this.sortedByAttribute = "quality";
                }
            } else if (algorithm.equals("mergeSort")) { //mergeSort selector
                mergeSort(data, attribute);
                this.sortedByAttribute = attribute;

            } else if (algorithm.equals("quickSort")) { //quickSort selector
                quickSort(data,0, data.size() - 1, attribute);
                this.sortedByAttribute = attribute;
            } else { //Collections.sort()
                if (!attribute.equals("category") && !attribute.equals("quality")) { //SortPrice
                    Collections.sort(data, (g1, g2) -> Integer.compare(g1.price, g2.price));
                    this.sortedByAttribute = "price";
                } else if (attribute.equals("category")) {
                    Collections.sort(data, (g1, g2) -> g1.category.compareTo(g2.category));
                    this.sortedByAttribute = "category";
                } else {
                    Collections.sort(data, (g1, g2) -> Integer.compare(g1.quality, g2.quality));
                    this.sortedByAttribute = "quality";
                }
            }
        }
    }

    public class GenerateData{

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Game> data = new ArrayList<>();
        Dataset DataSeted;

        while (true) {
            System.out.println("\n=== Menú ===");
            System.out.println("1. Generar Datos de Dataset");
            System.out.println("2. Usar Dataset");
            System.out.println("3. Obtener juegos de precio especifico");
            System.out.println("4. Obtener juegos por rango de precios");
            System.out.println("5. Obtener juegos por categoria");
            System.out.println("6. Obtener juegos por calidad");
            System.out.println("7. Ordenar Dataset");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            int opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    //usar GenerateData (y que guarde el dataset creado en un texto) por ci a caso pesca la carpeta del proyecto (LAB3 EDA) desde git, no solo el codigo
                    //porque estaba viendo y cuando lo guardas en texto, se guarda en la carpeta src del proyecto
                    break;
                case 2:
                    //el usuario elije el archivo de dataset
                    //define a data = (archivo elejido)
                    DataSeted = new Dataset(data);
                    break;
                case 3: //falta poner que imprima cuanto se demora en buscar nomas
                    System.out.println("Ingrese precio a buscar: ");
                    int price = sc.nextInt();
                    ArrayList<Game> a = DataSeted.getGamesByPrice(price);
                    break;
                case 4: //falta poner que imprima cuanto se demora en buscar nomas
                    System.out.println("Ingrese precio minimo a buscar: ");
                    int priceMin = sc.nextInt();
                    System.out.println("Ingrese precio maximo a buscar: ");
                    int priceMax = sc.nextInt();
                    ArrayList<Game> a = DataSeted.getGamesByPriceRange(priceMin, priceMax);
                    break;
                case 5: //falta poner que imprima cuanto se demora en buscar nomas
                    System.out.println("Ingrese categoria a buscar: ");
                    String category = sc.nextLine();
                    ArrayList<Game> a = DataSeted.getGamesByCategory(category);
                    break;
                case 6: //falta poner que imprima cuanto se demora en buscar nomas
                    System.out.println("Ingrese calidad a buscar (0-100): ");
                    int quality = sc.nextInt();
                    ArrayList<Game> a = DataSeted.getGamesByQuality(quality);
                    break;
                case 7: //falta poner que imprima cuanto se demora en ordenar nomas
                    System.out.println("\n=== Atributo a ordenar ===");
                    System.out.println("1. Price (otro)");
                    System.out.println("2. Category");
                    System.out.println("3. Quality");
                    System.out.print("Opción: ");
                    int atributeOpcion = sc.nextInt();
                    sc.nextLine();
                    String attribute = switch (atributeOpcion){
                        case 1 -> "price";
                        case 2 -> "category";
                        case 3 -> "quality";
                        default -> {
                            System.out.println("Opción inválida.");
                            yield null;
                        }
                    };
                    if (attribute.isEmpty()) {
                        break;
                    }
                    System.out.println("\n=== Algoritmo a usar ===");
                    System.out.println("1. Bubble Sort");
                    System.out.println("2. Insertion Sort");
                    System.out.println("3. Selection Sort");
                    System.out.println("4. Merge Sort");
                    System.out.println("5. Quick Sort");
                    System.out.println("0. Salir");
                    System.out.print("Opción: ");
                    int algorithmOpcion = sc.nextInt();
                    String algorithm = switch (algorithmOpcion) {
                        case 1 -> "bubbleSort";
                        case 2 -> "insertionSort";
                        case 3 -> "selectionSort";
                        case 4 -> "mergeSort";
                        case 5 -> "quickSort";
                        default -> {
                            System.out.println("Opción inválida.");
                            yield null;
                        }
                    };
                    if (algorithm.isEmpty()) {
                        break;
                    }
                    DataSeted.sortByAlgorithm(algorithm, attribute);
                    System.out.println("\nOrdenado por: " +attribute + "\n Utilizando: " + algorithm);
                    break;
                case 0:
                    System.out.println(" Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}
