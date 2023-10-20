package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HomeScreen {
    public static HashMap<String, Product> inventory = new HashMap<String, Product>();
    public static HashMap<String, itemsCart> cartHashMap = new HashMap<String, itemsCart>();
    static int itemsPurchased = 0;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {


        System.out.println("Hello! Welcome to Human's Online Store!");
        int userInput;
        do {
            System.out.println("What would you like to do?\n (1)Display the products in our inventory\n (2)Display the products in your cart\n (3) Exit");
            userInput = scanner.nextInt();

            switch (userInput) {
                case 1:
                    displayProducts();
                    break;
                case 2:
                    displayCart();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("\n Please choose a valid option. (1),(2),(3)");
            }
        } while (userInput != 3);
    }

    public static void displayCart() throws IOException {
        int userInput;
        System.out.println("What would you like to do?\n (1) Check current cart\n (2) Checkout\n (3) Search for products");
        userInput = scanner.nextInt();

        switch (userInput) {
            case 1:
                currentCart();
                break;
            case 2:
                checkoutItems();
                break;
            case 3:
                productSearch();
            default:
                System.out.println("Please put a valid answer");
        }

    }

    public static void displayProducts() throws IOException {

        String input;
        boolean firstLine = true;
        FileReader fileReader = new FileReader("src/main/resources/products.csv");
        BufferedReader bufReader = new BufferedReader(fileReader);

        while ((input = bufReader.readLine()) != null) {
            String[] inventoryReader = input.split("\\|");
            if (!inventoryReader[0].contains("SKU")) {
                String SKU = inventoryReader[0];
                String name = inventoryReader[1];
                double productPrice = Double.parseDouble(inventoryReader[2]);
                String department = inventoryReader[3];
                inventory.put(name.toLowerCase(), new Product(SKU, name, productPrice, department));
            }
        }
        for (Product p : inventory.values()) {
            System.out.printf("id: %s %s - Price: $%.2f %s\n", p.getSKU(), p.getName(), p.getPrice(), p.getDepartment());
        }
        fileReader.close();
        bufReader.close();
    }

    public static void currentCart() throws IOException {
        int userInput;
        do {
            System.out.println("What you like to do?\n (1) Add items to cart\n (2) Remove items from cart\n (3) Checkout");
            userInput = scanner.nextInt();
            scanner.nextLine();

            switch (userInput) {
                case 1:
                    addItems();
                    break;
                case 2:
                    removeItems();
                    break;
                case 3:
                    checkoutItems();
                default:
                    System.out.println("Please choose a valid option");
            }
        } while (userInput != 3);
    }

    public static void addItems() throws IOException {
        String productName;

        do {
            System.out.println("Enter a product name: ");
            productName = scanner.nextLine().toLowerCase();
        } while (!inventory.containsKey(productName));
        System.out.println("How much would you like ? ");
        int quantity = Integer.parseInt(scanner.nextLine());
        cartHashMap.put(productName, new itemsCart(quantity, inventory.get(productName)));
        System.out.println("Item is now in cart");
    }

    public static void checkoutItems() {
        System.out.println("Thanks for shopping!");
        System.exit(0);
    }

    public static void removeItems() throws IOException {
        String productName;
        boolean exist = false;
        int remove = 0;
        do {
            System.out.println("Enter the product name");
            productName = scanner.nextLine();
            for (Map.Entry<String, itemsCart> re : cartHashMap.entrySet()) {
                if (re.getValue().getProduct().getName().equals(productName)) {
                    exist = true;
                    cartHashMap.remove(re.getKey());
                    System.out.println("It has been removed");
                }
            }
            if (!exist) {
                System.out.println("System error, you don't have this in your cart.");
            }
        } while (!exist);
    }

    public static void productSearch() throws IOException {
        int userChoice = 0;
        String productName;
        String department;
        do {
            System.out.println("How do you want to search/filter?\n (1) Product name\n (2) Product price\n (3) Product department\n (4) Exit");
            userChoice = scanner.nextInt();
            switch (userChoice) {
                case 1:
                    scanner.nextLine();
                    do {
                        System.out.println("What product are you looking for?");
                        productName = scanner.nextLine().toUpperCase();
                        if (!inventory.containsKey(productName)) {
                            System.out.println("Product doesn't exist");
                        }
                    }

                    while (!inventory.containsKey(productName));
                    System.out.println(inventory.get(productName).toString());
                    break;
                case 2:
                    System.out.println("What is your min price");
                    double minPrice = scanner.nextDouble();
                    System.out.println("What is your max price");
                    double maxPrice = scanner.nextDouble();
                    for (Map.Entry<String, Product> well : inventory.entrySet()) {
                        if (well.getValue().getPrice() <= maxPrice && well.getValue().getPrice() >= minPrice) {
                            System.out.println(well.getValue().toString());
                        }
                    }
                    break;
                case 3:
                    String dpChoice;
                    do {
                        System.out.println("Which department do you want?\n Audio Video\n Computers\n Games\n Electronics\n");
                        dpChoice = scanner.nextLine().trim().toLowerCase();
                        if (!dpChoice.equals("audio video") && !dpChoice.equals("computers") && !dpChoice.equals("games") && !dpChoice.equals("electronics")) {
                            System.out.println("Invalid input");
                        }

                    }
                    while (!dpChoice.equals("audio video") && !dpChoice.equals("computers") && !dpChoice.equals("games") && !dpChoice.equals("electronics"));
                    for (Map.Entry<String, Product> search : inventory.entrySet()) {
                        if (search.getValue().getDepartment().equalsIgnoreCase(dpChoice)) {
                            System.out.println(search.getValue().toString());
                        }
                    }
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Please enter a valid input");

            }
        }
        while (userChoice != 4);


    }
}