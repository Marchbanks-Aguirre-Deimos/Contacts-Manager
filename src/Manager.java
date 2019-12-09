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
        Path dataFile = Paths.get(directory,file);
        Scanner scanner = new Scanner(System.in);

        List<Contact> contacts = Arrays.asList(new Contact("Colin","210-827-0646"), new Contact("Niloc","646-072-8012"));

        List<String> contactStrings = new ArrayList<>();

        for (Contact contact : contacts){
            contactStrings.add(contact.toString());
        }

        try{

            if(!Files.exists(dataDirectory)){
                Files.createDirectories(dataDirectory);
            }

            if(!Files.exists(dataFile)){
                Files.createFile(dataFile);
            }

            Path filepath = Paths.get(directory,file);

            Files.write(filepath, contactStrings);

            System.out.println("Welcome to the Contact Manager\n1. View Contacts\n2. Add a new Contact\n3. Search for a Contact by name\n4. Delete an existing contact by name\n5. Exit");



        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    static void showContacts(List<String> contacts){
        for(String contact : contacts){
            System.out.println(contact);
        }
    }

    static void addContact(Path filePath , Contact contact) {
        try {
            Files.write(
                    filePath,
                    Collections.singletonList(contact.toString()),
                    StandardOpenOption.APPEND
            );
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    static void searchByName(Path filepath,String name){
        try {
            List<String> searchList = Files.readAllLines(filepath);
            for(String result : searchList){
                Contact tempContact = formatContact(result);
                if(tempContact.getName().equalsIgnoreCase(name)){
                    System.out.println(tempContact.toString());
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    static void deleteContact(Path filepath, String name){
        try {
            List<String> searchList = Files.readAllLines(filepath);
            List<String> tempList = new ArrayList<>();
            for(String result : searchList){
                Contact tempContact = formatContact(result);
                if(tempContact.getName().equalsIgnoreCase(name)){
                    continue;
                }
                tempList.add(result);
            }
            Files.write(filepath,tempList);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    static Contact formatContact(String contactFromFile){
        String[] temp = contactFromFile.split(":");
        return new Contact(temp[0],temp[1]);
    }

}
