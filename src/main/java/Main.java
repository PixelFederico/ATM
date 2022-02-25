import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        MySQL.init();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserire pin");
        String pin = scanner.nextLine();

        String hashed = Hashing.sha256()
                .hashString(pin, StandardCharsets.UTF_8)
                .toString();
        if(pin.equals("0")){
            System.out.println("Inserire nome per la creazione dell'user");
            String nome = scanner.nextLine();
            String passwd = RandomPassword.generateCommonLangPassword();
            String passwdhashed = Hashing.sha256()
                    .hashString(passwd, StandardCharsets.UTF_8)
                    .toString();

            User user = new User(nome, 500.50, 500.50, passwdhashed);
            MySQL.addUser(user);
            System.out.println("Utente generato con successo! Pin: "+passwd+" conservalo bene!");

        }else{
            if(MySQL.exist(hashed)){
                Session.generateSession(MySQL.getUser(hashed));
            }else{
                System.out.println("Pin non corretto!");
            }
        }
    }
}
