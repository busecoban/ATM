//author:Buse ÇOBAN date:13.03.2022
import java.util.Random;
public class ATM2_1 {

    public static void main(String[] args) {
        Account a = new Account("1234",100);
        a.withdraw(50);
        System.out.println(a);

        PersonalAccount pa= new PersonalAccount("9876","Joseph","ledet");
        pa.deposit(150);
        System.out.println(pa);

        System.out.println("PIN is " + pa.getPIN());

        BusinessAccount ba= new BusinessAccount("5678",1000, 0.09);
        ba.deposit(ba.calculateInterest());
        System.out.println(ba);
        ba.withdraw(100);
        System.out.println(ba);

        Customer cu= new Customer("John","Smith");
        System.out.println(cu);
        cu.openAccount("3456");
        cu.getAccount().deposit(123);
        System.out.println(cu.getAccount());

        Company co = new Company("Akdeniz University");
        System.out.println(co);
        co.openAccount("6543", 0.05);
        co.getAccount().deposit(321);
        System.out.println(co.getAccount());


    }
}

class Account{
    private String acctNum;
    private double balance;

    public Account(){}
    public Account(String acctNum){
        this.acctNum=acctNum;
        balance=0;
    }

    public Account(String acctNum,double balance){
        this(acctNum);
        if(balance>0){
            this.balance=balance;
        }else
           balance=0;
    }



    String getAccount() {
        return this.acctNum;
    }
    public double getBalance() {
        return this.balance;
    }



    public void deposit (double depositAmount){

        if(depositAmount>0)
            balance+=depositAmount  ;
    }



    public void withdraw (double withdrawalAmount){
        if(withdrawalAmount>0)
            balance-=withdrawalAmount;
    }



    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Account "+getAccount()+" has "+getBalance();
    }
}



class PersonalAccount extends Account {
    private String name,surname,PIN;
    public PersonalAccount (){}
    public PersonalAccount(String acctNum,String name,String surname){
        super(acctNum);
        this.name=name;
        this.surname=surname;
        Random random=new Random();
        this.PIN=String.format("%04d",random.nextInt(10000));
    }
    public PersonalAccount(String acctNum,String name,String surname,double balance ){
        super(acctNum,balance);
        this.name=name;
        this.surname=surname;
        Random random=new Random();
        this.PIN=String.format("%04d",random.nextInt(10000));
    }



    public String getName (){
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname(){
        return this.surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getPIN(){
        return this.PIN;
    }
    public void setPIN(String PIN) {
        this.PIN = PIN;
    }



    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Account " + getAccount() + " belonging to " + getName() + " " + getSurname().toUpperCase() + " has " + getBalance();
    }
}



class BusinessAccount extends Account{

    private double interestRate;

    public BusinessAccount(){}
    public BusinessAccount(String acctNum, double interestRate){
        super(acctNum);
        this.interestRate=interestRate;
    }
    public BusinessAccount(String acctNum,double balance,double interestRate){
        super(acctNum,balance);
        this.interestRate=interestRate;
    }



    public double getInterestRate() {
        return this.interestRate;
    }
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }



    public double calculateInterest(){
        return getBalance()*interestRate;
    }
}

class Customer {
    private String name,surname;

    private PersonalAccount personal;
    public Customer(){}
    public Customer(String name,String surname){
        this.name=name;
        this.surname=surname;
    }


    public String getSurname() {
        return this.surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public void openAccount(String acctNum){
        this.personal=new PersonalAccount(acctNum,getName(),getSurname());
    }
    public PersonalAccount getAccount(){
        return personal;
    }


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return getName()+ " "+ getSurname().toUpperCase();
    }
}



class Company{
    private String name;
    private BusinessAccount acct;

    public Company(String name){
        this.name=name;
    }


    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }


    public void openAccount(String acctNum,double interestRate){
        this.acct=new BusinessAccount(acctNum,0,interestRate);
    }
    public BusinessAccount getAccount(){
        return acct;
    }


    @Override
    public String toString(){
        //TODO Auto-generated method stub
        return getName();
    }
}

