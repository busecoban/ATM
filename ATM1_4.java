//@author:Buse Çoban,@version:22.12.2021

import java.io.*;
import java.util.Scanner;

public class ATM1_4 {
    public static void main(String[] args) throws Exception {

        int [] acctNums = new int[countAccounts(args[0]+"_AccountInfo.txt")];
        String [] acctNames = new String[countAccounts(args[0]+"_AccountInfo.txt")];
        String [] acctSurnames = new String[countAccounts(args[0]+"_AccountInfo.txt")];
        double [] acctBalances = new double[countAccounts(args[0]+"_AccountInfo.txt")];

        int numOfAccounts;
        numOfAccounts = ATM1_4.countAccounts(args[0]+"_AccountInfo.txt");
        System.out.println("The number of account is "+ numOfAccounts);
        ATM1_4.readAccountInfo(acctNums,acctNames,acctSurnames,acctBalances,args[0]+"_AccountInfo.txt");
        System.out.println("The information in the file is :");
        System.out.println("Number\tName\tBalance");

        for (int i = 0; i <acctNums.length ; i++)

            System.out.println(acctNums[i]+"\t"+acctNames[i]+"\t"+acctSurnames[i]+"\t"+acctBalances[i]);




        File log = new File("Assignment4.log.txt");
        if(!log.exists()){
            log.createNewFile();
        }


            System.out.println("The deposit is succesful : " +ATM1_4.deposit(acctBalances,0,100));
            System.out.println("The new balance for the first account is" + acctBalances[0]);
            System.out.println("The withdrawal is succesful :" + ATM1_4.withdrawal(acctBalances,1,100));
            System.out.println("The new balance for the second account is "+acctBalances[1]);
            System.out.println("The return value of transfering 150 from the first account to second account is :" +
                    ATM1_4.transfer(acctNums,acctBalances,12345,67890,150));
            System.out.println("The new balance for the first account is " + acctBalances[0]);
            System.out.println("The new balance for the second account is " + acctBalances[1]);
            System.out.println("The return value of transfering 150 from the last account to second account is :" +
                    ATM1_4.transfer(acctNums,acctBalances,98765,67890,150));
            System.out.println("The new balance for the last account is "+acctBalances[5]);
            System.out.println("The new balance for the second account is "+acctBalances[1]);
         ATM1_4.writeAccountInfo(acctNums,acctNames,acctSurnames,acctBalances,args[0]+"_AccountInfoOut.txt");




    }


    public static int countAccounts(String filename) throws Exception {
        //File file = new File(filename);
        Scanner input = new Scanner(new File(filename));
        int numOfAccounts=0;
        while (input.hasNext()) {
            String numOfLine=input.nextLine();
            numOfAccounts++;
        }
        input.close();

        return numOfAccounts;

    }


    public static void readAccountInfo(int[] acctNums, String[] names, String[] surnames, double[] balances,
                                       String filename) throws FileNotFoundException {
        int i = 0 ;
        Scanner input = new Scanner(new File(filename));

            while (input.hasNext()) {
                acctNums[i] = input.nextInt();
                names[i] = input.next();
                surnames[i] =input.next();
                balances[i] = input.nextDouble();

                ++i;
            }
            input.close();

    }

    public static void writeAccountInfo(int[]acctNums,String[]names,String[]surnames,double[]balances,String filename)throws Exception{
        File file = new File(filename);
        PrintWriter output = new PrintWriter(file);

        for (int i = 0; i <acctNums.length ; i++) {
            output.print(acctNums[i] + " ");
            output.print(names[i] + " ");
            output.print(surnames[i] + " ");
            output.print(balances[i] + " ");
            output.println(" ");
        }
        output.close();
    }

    public static boolean deposit(double[] balances,int index,double amount){
        if(amount>0){
            balances[index]=balances[index]+amount;

            return true;
        }else{
            return false;
        }
    }

    public static boolean withdrawal(double[]balances,int index,double amount){
        if(amount>0 && amount<=balances[index]){
            balances[index]=balances[index]-amount;

            return true;
        }else {
            return false;
        }
    }

    public static int transfer(int[] acctNums,double[] balances,int acctNumFrom,int acctNumTo,double amount) {
        int indexOfFrom=findAcct(acctNumFrom,acctNums);
        int indexOfTo=findAcct(acctNumTo,acctNums);

        if (indexOfFrom>=0 && balances[indexOfFrom]>amount && indexOfTo>=0){
            balances[indexOfFrom]=balances[indexOfFrom]-amount;
            balances[indexOfTo]=balances[indexOfTo]+amount;
            return 0 ;
        }
        else if(indexOfTo==-1)
            return 1;
        else if (indexOfFrom==-1)
            return 2;
        else
            return 3;
    }

    public static int findAcct(int acctNums,int[]acctNum) {
        int result = -1;
        for (int i = 0; i < acctNum.length; i++)
        {
           if (acctNum[i]==acctNums){
               result=i;
           }
        }
        return result;
    }

    public static boolean isDepositValid(double amount) {
        if(amount > 0){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isWithdrawalValid(double balance,double amount) {
        if(amount > 0 && amount <= balance){
            return true;
        }else{
            return false;
        }
    }




}

