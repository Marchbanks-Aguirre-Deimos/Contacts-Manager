import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;


public class Manager {

    final static String directory = "data";
    final static String file = "contacts.txt";

    public static void main(String[] args) {

        Path dataDirectory = Paths.get(directory);
        Path dataFile = Paths.get(directory, file);
        Scanner scanner = new Scanner(System.in);
        Input input = new Input();

        List<Contact> contacts = Arrays.asList(new Contact("Colin", "2108270646"), new Contact("Niloc", "6460728012"), new Contact("Bob", "6664444"));

        List<String> contactStrings = new ArrayList<>();

        for (Contact contact : contacts) {
            contactStrings.add(contact.toString());
        }

        boolean flag = true;


        try {

            if (!Files.exists(dataDirectory)) {
                Files.createDirectories(dataDirectory);
            }

            if (!Files.exists(dataFile)) {
                Files.createFile(dataFile);
            }

            Path filepath = Paths.get(directory, file);

            Files.write(filepath, contactStrings);
            while (flag) {
                List<String> lines = Files.readAllLines(filepath);
                System.out.println("Welcome to the Contact Manager\n1. View Contacts\n2. Add a new Contact\n3. Search for a Contact by name\n4. Delete an existing contact by name\n5. Exit");
                int inputNum = scanner.nextInt();
                menuSelect(inputNum, lines, filepath);
                System.out.println();
                System.out.println("Would you like to continue?");
                flag = input.yesNo();
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    static void menuSelect(int input, List<String> lines, Path filepath) throws IOException {
        Scanner scanner = new Scanner(System.in);
        switch (input) {
            case 1:
                showContacts(lines);
                break;
            case 2:
                System.out.println("What is the name of the contact you'd like to add?");
                String nameAdded = scanner.nextLine();
                System.out.println("What is the number for this new contact?");
                String number = scanner.nextLine();
                addContact(filepath, new Contact(nameAdded, number));
                lines = Files.readAllLines(filepath);
                showContacts(lines);
                break;
            case 3:
                System.out.println("What is the name of the contact you're looking for?");
                String nameSearched = scanner.nextLine();
                searchByName(filepath, nameSearched);
                break;
            case 4:
                System.out.println("What is the name of the contact you're trying to delete?");
                String nameToDelete = scanner.nextLine();
                deleteContact(filepath, nameToDelete);
                lines = Files.readAllLines(filepath);
                showContacts(lines);
                break;
            case 5:
                System.exit(0);
        }
    }


    static void showContacts(List<String> lines) {
        System.out.println();
        System.out.println("Name             | Phone number   |\n- - - - - - - - - - - - - - - - - - ");
        for (String line : lines) {
            Contact name = formatContact(line);
            System.out.println(name.formattedString());
        }
        System.out.println();
    }

    static void addContact(Path filePath, Contact contact) {
        Input input = new Input();
        Scanner scanner = new Scanner(System.in);
        List<String> contactStrings = new ArrayList<>();

        try {
            List<Contact> tempList = new ArrayList<>();
            List<String> lines = Files.readAllLines(filePath);
            for (String lineResult : lines) {
                if (!formatContact(lineResult).getName().equalsIgnoreCase(contact.getName())) {
                    tempList.add(formatContact(lineResult));
                } else {
                    System.out.printf("There is a contact already name %s. Do you want to overwrite it?", contact.getName());
                    if (input.yesNo()) {
                        tempList.add(contact);
                    } else {
                        System.out.println("What is the name of the contact now?");
                        contact.setName(scanner.nextLine());
                        tempList.add(formatContact(lineResult));
                        tempList.add(contact);
                    }
                }
            }
            for (Contact contactFromTempList : tempList) {
                contactStrings.add(contactFromTempList.toString());
            }
            Files.write(
                    filePath,
                    contactStrings
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void searchByName(Path filepath, String name) {
        try {
            List<String> searchList = Files.readAllLines(filepath);
            for (String result : searchList) {
                Contact tempContact = formatContact(result);
                if (tempContact.getName().equalsIgnoreCase(name)) {
                    System.out.println();
                    System.out.println("Name             | Phone number   |\n- - - - - - - - - - - - - - - - - - ");
                    System.out.println(tempContact.formattedString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void deleteContact(Path filepath, String name) {
        try {
            List<String> searchList = Files.readAllLines(filepath);
            List<String> tempList = new ArrayList<>();
            for (String result : searchList) {
                Contact tempContact = formatContact(result);
                if (tempContact.getName().equalsIgnoreCase(name)) {
                    continue;
                }
                tempList.add(result);
            }
            Files.write(filepath, tempList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Contact formatContact(String contactFromFile) {
        String[] temp = contactFromFile.split(":");
        return new Contact(temp[0], temp[1]);
    }

}
