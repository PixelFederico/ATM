public class User extends AbstractUser {
    double wallet;
    double bank;
    String name;
    String pin;

    public User(String name, double wallet, double bank, String pin){
        this.name = name;
        this.bank = bank;
        this.wallet = wallet;
        this.pin = pin;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;

    }

    @Override
    public double getAccountMoney() {
        return bank;
    }

    @Override
    public void setAccountMoney(double bank) {
        this.bank = bank;
    }

    @Override
    public double getWalletMoney() {
        return wallet;
    }

    @Override
    public void setWalletMoney(double wallet) {
        this.wallet = wallet;
    }

    @Override
    public double getPin() {
        return wallet;
    }

    @Override
    public void setPin(double wallet) {
        this.wallet = wallet;
    }
}
