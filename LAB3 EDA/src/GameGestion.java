import java.io.*;
import java.util.*;

public class GameGestion {
    public class Game{
        String name;
        String category;
        int price;
        int quality;

        Game(String name,String category,int price,int quatity){
            this.name = name;
            this.category = category;
            this.price = price;
            this.quality = quatity;
        }
    }

    public class Dataset{
        ArrayList<Game> data;
        String sortedByAttribute;

        Dataset(ArrayList<Game> data){
            this.data = data;
        }

        ArrayList<Game> getGamesByPrice(int price){
            ArrayList<Game> gamesPrice = new ArrayList<>();
            int start = 0;
            int end = data.size() - 1;
            int StartCounter = 0;

            if(sortedByAttribute.equals("price")){
                while(start <= end) {
                    int middle = (start + end) / 2;
                    if(data.get(middle).price == price && (middle == 0 || data.get(middle - 1).price != price)){
                        StartCounter = middle;
                        break;
                    } else if(data.get(middle).price > price){
                        end = middle - 1;
                    } else {
                        start = middle + 1;
                    }
                }
                for(int i = StartCounter; i < data.size(); i++) {
                    if (data.get(i).price == price) {
                        gamesPrice.add(data.get(i));
                    }
                }
            } else {
                for(Game g : data) {
                    if (g.price == price) {
                        gamesPrice.add(g);
                    }
                }
            }
            return gamesPrice;
        }

        ArrayList<Game> getGamesByPriceRange(int lowerPrice, int higherPrice){
            ArrayList<Game> gamesRangePrice = new ArrayList<>();
            int start = 0;
            int end = data.size() - 1;
            int StartCounter = 0;
            if(sortedByAttribute.equals("price")){
                while(start <= end){
                    int middle = (start + end) / 2;
                    if(data.get(middle).price == lowerPrice && (middle == 0 || data.get(middle - 1).price != lowerPrice)){
                        StartCounter = middle;
                        break;
                    } else if(data.get(middle).price > lowerPrice){
                        end = middle - 1;
                    } else {
                        start = middle + 1;
                    }
                }
                for(int i = StartCounter; i < data.size(); i++) {
                    if (data.get(i).price >= lowerPrice && data.get(i).price <= higherPrice) {
                        gamesRangePrice.add(data.get(i));
                    }
                }
            } else {
                for(Game g : data) {
                    if (g.price <= lowerPrice && g.price >= higherPrice) {
                        gamesRangePrice.add(g);
                    }
                }
            }
            return gamesRangePrice;
        }

        ArrayList<Game> getGamesByCategory(String category){
            ArrayList<Game> gamesCategory = new ArrayList<>();
            int start = 0;
            int end = data.size() - 1;
            int StartCounter = 0;
            if(sortedByAttribute.equals("category")){
                while(start <= end){
                    int middle = (start + end) / 2;
                    if (data.get(middle).category.equals(category) && (middle == 0 || !data.get(middle - 1).category.equals(category))){
                        StartCounter = middle;
                        break;
                    } else if(data.get(middle).category.equals(category)){
                        end = middle - 1;
                    } else {
                        start = middle + 1;
                    }
                }
                for(int i = StartCounter; i < data.size(); i++) {
                    if (data.get(i).category.equals(category)) {
                        gamesCategory.add(data.get(i));
                    } else break;
                }
            } else {
                for(Game g : data) {
                    if (g.category.equals(category)) {
                        gamesCategory.add(g);
                    }
                }
            }
            return gamesCategory;
        }

        ArrayList<Game> getGamesByQuality(int quality){
            ArrayList<Game> gamesQuality = new ArrayList<>();
            int start = 0;
            int end = data.size() - 1;
            int StartCounter = 0;
            if(sortedByAttribute.equals("quality")){
                while(start <= end){
                    int middle = (start + end) / 2;
                    if(data.get(middle).quality == quality && (middle == 0 || data.get(middle - 1).quality != quality)){
                        StartCounter = middle;
                        break;
                    } else if(data.get(middle).quality > quality){
                        end = middle - 1;
                    } else {
                        start = middle + 1;
                    }
                }
                for(int i = StartCounter; i < data.size(); i++) {
                    if (data.get(i).quality == quality) {
                        gamesQuality.add(data.get(i));
                    } else break;
                }
            } else {
                for(Game g : data) {
                    if (g.quality == quality) {
                        gamesQuality.add(g);
                    }
                }
            }
            return gamesQuality;
        }

        void sortByAlgorithm(String algorithm, String attribute){

            if(algorithm.equals("bubbleSort")){ //BubbleSort selector
                if(!attribute.equals("category") && !attribute.equals("quality")){ //SortPrice
                    for(int i = 0 ; i < data.size() - 1 ; i++){
                        for(int j = 0; j < data.size() - i - 1; j++){
                            if(data.get(j).price > data.get(j + 1).price){
                                Collections.swap(data, j, j + 1);
                            }
                        }
                    }
                    this.sortedByAttribute = "price";
                } else if(attribute.equals("category")){ //SortCategory
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
                    }
                    this.sortedByAttribute = "quality";
                }
            } else if(algorithm.equals("insertionSort")){ //insertionSort selector
                if(!attribute.equals("category") && !attribute.equals("quality")){ //SortPrice
                    for (int i = 1 ; i < data.size(); i++){
                        int j = i;
                        while (j > 0 && data.get(j - 1).price > data.get(j).price){
                            Collections.swap(data, j, j - 1);
                            j--;
                        }
                    }
                    this.sortedByAttribute = "price";
                } else if(attribute.equals("category")){ //SortCategory
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
                        while (j > 0 && data.get(j -1).quality > data.get(j).quality){
                            Collections.swap(data, j, j - 1);
                            j--;
                        }
                    }
                    this.sortedByAttribute = "quality";
                }
            } else if(algorithm.equals("selectionSort")){ //selectionSort selector
                if(!attribute.equals("category") && !attribute.equals("quality")){ //SortPrice

                    this.sortedByAttribute = "price";
                } else if(attribute.equals("category")){ //SortCategory

                    this.sortedByAttribute = "category";
                } else { //SortQuality

                    this.sortedByAttribute = "quality";
                }
            } else if(algorithm.equals("mergeSort")){ //mergeSort selector
                if(!attribute.equals("category") && !attribute.equals("quality")){ //SortPrice

                    this.sortedByAttribute = "price";
                } else if(attribute.equals("category")){ //SortCategory

                    this.sortedByAttribute = "category";
                } else { //SortQuality

                    this.sortedByAttribute = "quality";
                }
            } else if(algorithm.equals("quickSort")){ //quickSort selector
                if(!attribute.equals("category") && !attribute.equals("quality")){ //SortPrice

                    this.sortedByAttribute = "price";
                } else if(attribute.equals("category")){ //SortCategory

                    this.sortedByAttribute = "category";
                } else { //SortQuality

                    this.sortedByAttribute = "quality";
                }
            } else { //Collections.sort()
                if(!attribute.equals("category") && !attribute.equals("quality")){ //SortPrice

                    this.sortedByAttribute = "price";
                } else if(attribute.equals("category")){ //SortCategory

                    this.sortedByAttribute = "category";
                } else { //SortQuality

                    this.sortedByAttribute = "quality";
                }
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}