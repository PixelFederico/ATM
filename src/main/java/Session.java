public class Session {
    public static void generateSession(User user){
        String name = user.name;
        double bank = user.bank;
        double wallet = user.wallet;
        System.out.println("Benvenuto, "+name+"!");
        System.out.println("Al momento possiedi: "+bank+" soldi in banca e: "+wallet+" contanti!");
        System.out.println("Seleziona un'azione: 1 Ritira - 2 Deposita - 3 Esci - 4 Cancella l'account");
    }
}
