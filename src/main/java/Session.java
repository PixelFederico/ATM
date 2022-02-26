import java.util.Scanner;

public class Session {
    public static void generateSession(String pin){
        Scanner scanner = new Scanner(System.in);
        int azione = 0;
        do{
            User user = MySQL.getUser(pin);
            String name = user.name;
            double bank = user.bank;
            double wallet = user.wallet;
            System.out.println("Benvenuto, "+name+"!");
            System.out.println("Al momento possiedi: "+bank+" soldi in banca e: "+wallet+" contanti!");
            System.out.println("Seleziona un'azione: 1 Ritira - 2 Deposita - 3 Esci - 4 Cancella l'account");
            azione = scanner.nextInt();
            switch (azione) {
                case 1:
                    System.out.println("Inserisci l'importo da ritirare");
                    double ritiro = scanner.nextDouble();
                    MySQL.withdraw(user, ritiro);
                    break;
                case 2:
                    System.out.println("Inserisci l'importo da deposita");
                    double deposito = scanner.nextDouble();
                    MySQL.deposit(user, deposito);
                    break;
                case 4:
                    MySQL.deleteAccount(user);
                    azione=3;
                    break;
            }
        }while(azione!=3);
    }
}
