
import java.util.ArrayList;
import java.util.Random;

public class ATM2_2 {

    public static void main(String[] args) {

        Bank b = new Bank("My Bank", "My Bank's Address");
        b.addCompany(1, "Company 1");
        b.getCompany(1).openAccount("1234", 0.05);
        b.addAccount(b.getCompany(1).getAccount("1234"));
        b.getAccount("1234").deposit(500000);
        b.getCompany(1).getAccount("1234").deposit(500000);
        b.getCompany(1).openAccount("1235", 0.03);
        b.addAccount(b.getCompany(1).getAccount("1235"));
        b.getCompany(1).getAccount("1235").deposit(25000);
        b.addCompany(2, "Company 2");
        b.getCompany(2).openAccount("2345", 0.03);
        b.addAccount(b.getCompany(2).getAccount("2345"));
        b.getCompany(2).getAccount("2345").deposit(350);

        b.addCustomer(1, "Customer", "1");
        b.addCustomer(2, "Customer", "2");


        Customer c = b.getCustomer(1);
        c.openAccount("3456");
        c.openAccount("3457");
        c.getAccount("3456").deposit(150);
        c.getAccount("3457").deposit(250);
        c = b.getCustomer(2);
        c.openAccount("4567");
        c.getAccount("4567").deposit(1000);
        b.addAccount(c.getAccount("4567"));
        c = b.getCustomer(1);
        b.addAccount(c.getAccount("3456"));
        b.addAccount(c.getAccount("3457"));
        System.out.println(b.toString());




    }
}

class Bank {
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private ArrayList<Company> companies = new ArrayList<Company>();
    private ArrayList<Customer> customers = new ArrayList<Customer>();
    private ArrayList<Account> accounts = new ArrayList<Account>();


    Bank(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void addCustomer(int id, String name, String surname) {
        customers.add(new Customer(id, name, surname));
    }

    public void addCompany(int id, String name) {
        companies.add(new Company(id, name));
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }


    public Customer getCustomer(int id) throws CustomerNotFoundException {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId() == id) {
                return customers.get(i);
            }
        }
        throw new CustomerNotFoundException(id);
    }

    public Customer getCustomer(String name, String surname) throws CustomerNotFoundException {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getName().equals(name) && customers.get(i).getSurname().equals(surname)) {
                return customers.get(i);
            }
        }
        throw new CustomerNotFoundException(name, surname);
    }

    public Company getCompany(int id) throws CompanyNotFoundException {
        for (int i = 0; i < companies.size(); i++) {
            if (companies.get(i).getId() == id) {
                return companies.get(i);
            }
        }
        throw new CompanyNotFoundException(id);
    }

    public Company getCompany(String name) throws CompanyNotFoundException {
        for (int i = 0; i < companies.size(); i++) {
            if (companies.get(i).getName().equals(name)) {
                return companies.get(i);
            }
        }
        throw new CompanyNotFoundException(name);
    }

    public Account getAccount(String acctNum) throws AccountNotFoundException {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAcctNum().equals(acctNum)) {
                return accounts.get(i);
            }
        }
        throw new AccountNotFoundException(acctNum);
    }

    public void transferFunds(String accountFrom, String accountTo, double amount) throws RuntimeException {
        try {
            getAccount(accountFrom);
            getAccount(accountTo);
        } catch (AccountNotFoundException e) {
            throw e;
        }
        if (amount < 0) {
            throw new InvalidAmountException(amount);
        }
        if (getAccount(accountFrom).getBalance() < amount) {
            throw new InvalidAmountException(amount);
        }

        getAccount(accountFrom).withdrawal(amount);
        getAccount(accountTo).deposit(amount);


    }

    public String toString()
    {

        String firstline = name + "	" + address;
        String company = "";
        String custumer = "";
        for (int i = 0; i < companies.size(); i++) {
            Company c = companies.get(i);
            company += "   " + c.getName();
            company += c.getBusinessAccount();
        }
        for (int i = 0; i < customers.size(); i++) {
            Customer c = customers.get(i);
            custumer += "   " + c.getName();
            custumer += "   " + c.getSurname();
            custumer += c.getPersonalAccount();
        }
        firstline += "\n" + company  + custumer;
        return firstline;

    }

}
    class Account {
        private String acctNum;
        private double balance;

        public String getAcctNum() {
            return acctNum;
        }
        public void setAcctNum(String acctNum) {
            this.acctNum = acctNum;
        }
        public void setBalance(double balance) {
            this.balance = balance;
        }
        public double getBalance() {
            return this.balance;
        }

        public Account() {
        }

        public Account(String acctNum) {
            this.acctNum = acctNum;
            balance = 0;
        }

        public Account(String acctNum, double balance) {
            this(acctNum);
            if (balance > 0) {
                this.balance = balance;
            } else
                this.balance = 0;
        }


        public void deposit(double depositAmount) {
            if (depositAmount > 0) {
                balance += depositAmount;
            }
        }

        public void withdrawal(double withdrawalAmount) {
            if (withdrawalAmount > 0 && withdrawalAmount <= balance) {
                balance -= withdrawalAmount;
            }
        }

        @Override
        public String toString() {
            return "Account" + acctNum + " has " + balance;
        }
    }

    class PersonalAccount extends Account {

        private String name, surname, PIN;

        public String getName() {
            return this.name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getSurname() {
            return this.surname;
        }
        public void setSurname(String surname) {
            this.surname = surname;
        }
        public String getPIN() {
            return this.PIN;
        }
        public void setPIN(String PIN) {
            this.PIN = PIN;
        }

        public PersonalAccount() {
        }

        public PersonalAccount(String acctNum, String name, String surname) {
            super(acctNum);
            this.name = name;
            this.surname = surname;
            Random random = new Random();
            this.PIN = String.format("%04d", random.nextInt(10000));
        }

        public PersonalAccount(String acctNum, String name, String surname, double balance) {
            super(acctNum, balance);
            this.name = name;
            this.surname = surname;
            Random random = new Random();
            this.PIN = String.format("%04d", random.nextInt(10000));
        }

        @Override
        public String toString() {
            return "Account " + getAcctNum() + " belonging to " + name + " " + surname.toUpperCase()
                    + " has " + getBalance();
        }
    }


    class BusinessAccount extends Account {

        private double interestRate;

        public double getInterestRate() {
            return this.interestRate;
        }
        public void setInterestRate(double interestRate) {
            this.interestRate = interestRate;
        }

        public BusinessAccount() {
        }
        public BusinessAccount(String acctNum, double interestRate) {
            super(acctNum);
            this.interestRate = interestRate;
        }
        public BusinessAccount(String acctNum, double balance, double interestRate) {
            super(acctNum, balance);
            this.interestRate = interestRate;
        }

        public double calculateInterest() {
            return getBalance() * interestRate;
        }
    }

    class Customer {

        private int id;
        private String name, surname;
        private ArrayList<PersonalAccount> personalAccounts = new ArrayList<PersonalAccount>();


        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getSurname() {
            return this.surname;
        }
        public void setSurname(String Surname) {
            this.surname = Surname;
        }
        public String getName() {
            return this.name;
        }
        public void setName(String name) {
            this.name = name;
        }


        public Customer() {
        }

        public Customer(int id, String name, String surname) {
            this.id = id;
            this.name = name;
            this.surname = surname;
        }


        public void openAccount(String acctNum) {
            personalAccounts.add(new PersonalAccount(acctNum, name, surname));
        }

        public PersonalAccount getAccount(String acctNum) throws AccountNotFoundException {
            for (int i = 0; i < personalAccounts.size(); i++) {
                if (personalAccounts.get(i).getAcctNum().equals(acctNum)) {
                    return personalAccounts.get(i);
                }
            }
throw new AccountNotFoundException(acctNum);
        }

        public void closeAccount(String acctNum) throws RuntimeException {
            for (int i = 0; i < personalAccounts.size(); i++) {
                if (personalAccounts.get(i).getAcctNum().equals(acctNum)) {
                    if (personalAccounts.get(i).getBalance() > 0) {
                        throw new BalanceRemainingException(personalAccounts.get(i).getBalance());
                    } else {
                        personalAccounts.remove(i);
                    }

                } else {
                    throw new AccountNotFoundException(acctNum);
                }
            }
        }


        public String getPersonalAccount() {
            String accounts = "";
            for (int i = 0; i < personalAccounts.size(); i++) {
                accounts += "\n       "+ personalAccounts.get(i).getAcctNum() + "  "+ personalAccounts.get(i).getBalance() +"\n";
            }
            return accounts;
        }

        public String toString(){
            return name + " " + surname.toUpperCase();
        }
    }



    class Company {

        private int id;
        private String name;
        private ArrayList<BusinessAccount> businessAccounts = new ArrayList<BusinessAccount>();

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }


        public Company(int id, String name) {
            this.id = id;
            this.name = name;
        }


        public void openAccount(String acctNum, double interestRate) {
            BusinessAccount businessAccount = new BusinessAccount(acctNum, interestRate);
            businessAccounts.add(businessAccount);
        }

        public BusinessAccount getAccount(String acctNum) {
            for (int i = 0; i < businessAccounts.size(); i++) {
                if (businessAccounts.get(i).getAcctNum().equals(acctNum)) {
                    return businessAccounts.get(i);
                }
            }
            throw new AccountNotFoundException(acctNum);
        }

        public void closeAccount(String acctNum) {
            for (int i = 0; i < businessAccounts.size(); i++) {
                if (businessAccounts.get(i).getAcctNum().equals(acctNum)) {
                    if (businessAccounts.get(i).getBalance() > 0) {
                        throw new BalanceRemainingException(businessAccounts.get(i).getBalance());
                    }
                        businessAccounts.remove(i);
                }
            }
            throw new AccountNotFoundException(acctNum);
        }


        public String getBusinessAccount() {
            String accounts = "";
            for (int i = 0; i < businessAccounts.size(); i++) {
                BusinessAccount temp = businessAccounts.get(i);
                accounts +="\n    "+ temp.getAcctNum() + "  "+ temp.getBalance() +"   " + temp.getInterestRate()+"\n";
            }
            return accounts;
        }

        @Override
        public String toString() {
            return name;
        }


    }


    class AccountNotFoundException extends RuntimeException {

        private String acctNum;

        public AccountNotFoundException(String acctNum) {
            this.acctNum = acctNum;
            System.out.println(toString());

        }

        @Override
        public String toString() {
            return "AccountNotFoundException: " + acctNum;
        }
    }


    class BalanceRemainingException extends RuntimeException {
        private double balance;

        public double getBalance() {
            return balance;
        }

        public BalanceRemainingException(double balance) {
            this.balance = balance;
            System.out.println(toString());
        }

        public String toString() {
            return "BalanceRemainingException: " + balance;
        }
    }

    class CustomerNotFoundException extends RuntimeException {
        private int id;
        private String name, surname;

        public CustomerNotFoundException(int id) {
            this.name = null;
            this.surname = null;
            this.id = id;
            System.out.println(toString());
        }

        public CustomerNotFoundException(String name, String surname) {
            this.name = name;
            this.surname = surname;
            this.id = 0;
            System.out.println(toString());
        }

        @Override
        public String toString() {
            if (name == null && surname == null) {
                return "CustomerNotFoundException:name " + name + " " + surname;

            }

            if (id != 0) {
                return "CustomerNotFoundException:id " + id;
            }
            return null;
        }
    }

    class CompanyNotFoundException extends RuntimeException {
        private int id;
        private String name;

        public CompanyNotFoundException(int id) {
            this.name = null;
            this.id = id;
            System.out.println(toString());
        }

        public CompanyNotFoundException(String name) {
            this.name = name;
            this.id = 0;
            System.out.println(toString());
        }

        @Override
        public String toString() {
            if (name == null) {
                return "CompanyNotFoundException:name " + name + " " + id;
            }
                return "CustomerNotFoundException:id " + id;
            }

        }


    class InvalidAmountException extends RuntimeException {
        private double amount;

        public InvalidAmountException(double amount) {
            this.amount = amount;
            System.out.println(toString());
        }

        public String toString() {
            return "InvalidAmountException:" + amount;
        }
    }
