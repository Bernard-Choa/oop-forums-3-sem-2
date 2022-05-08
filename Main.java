package idk.java;
import pls.java.Account;
import pls.java.Bank;
import pls.java.Customer;
import java.util.Scanner;

// Container class for bankSelect() function
final class intendedAcc {
    public Bank bankChoice;
    public int customerNum;
    public intendedAcc(Bank bankChoice, int customerNum) {
        this.bankChoice = bankChoice;
        this.customerNum = customerNum;
    }
}

public class Main {
    // Handles
    static intendedAcc bankSelect(Bank[] accSelection) {
        Scanner userInp = new Scanner(System.in);
        System.out.print("Enter first name: ");
        String f = userInp.next();
        System.out.print("Enter last name: ");
        String l = userInp.next();
        System.out.println("Select bank: ");
        Bank bankChoice = null;
        int bankNum;
        int customerNum = 0;
        while (bankChoice == null) {
            for (int i=0;i<accSelection.length;i++) {
                System.out.println(i + " - " + accSelection[i].getBankName());
            }
            bankNum = userInp.nextInt();
            if (0 <= bankNum && bankNum < accSelection.length) {
                if (accSelection[bankNum].getNumOfCustomers() == 0) {
                    System.out.println("Your account is not found in " + accSelection[bankNum].getBankName());
                }
                for (int i=0;i<accSelection[bankNum].getNumOfCustomers();i++) {
                    if (accSelection[bankNum].getCustomer(i).getFirstName().equals(f) && accSelection[bankNum].getCustomer(i).getLastName().equals(l)) {
                        bankChoice = accSelection[bankNum];
                        customerNum = i;
                    }
                }
            } else {
                System.out.println("Invalid choice");
            }
        }
        return new intendedAcc(bankChoice, customerNum);
    }

    static boolean PINAuthentication(intendedAcc accLocInfo) {
        Scanner userInp = new Scanner(System.in);
        System.out.print("Enter PIN: ");
        int PIN = userInp.nextInt();
        if (PIN == accLocInfo.bankChoice.getCustomer(accLocInfo.customerNum).getAccount().getPIN()) {
            System.out.println("Authentication successful");
            return true;
        } else {
            System.out.println("Authentication failed");
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner userInp = new Scanner(System.in);
        int choice = 0;
        Bank bank1 = new Bank("Bank Central Asia");
        Bank bank2 = new Bank("Bank Mandiri");
        Bank bank3 = new Bank("Bank Negara Indonesia");
        Bank bank4 = new Bank("Bank Rakyat Indonesia");
        Bank[] listOfBanks = {bank1, bank2, bank3, bank4};
        while (choice < 6) {
            System.out.println("(1) Create new account");
            System.out.println("(2) Deposit into account");
            System.out.println("(3) Withdraw from account");
            System.out.println("(4) Transfer to another account");
            System.out.println("(5) Account info");
            System.out.println("(6) Exit");
            choice = userInp.nextInt();
            if (choice == 1) {
                System.out.println("Select bank: ");
                Bank bankChoice = null;
                int bankNum;
                while (bankChoice == null) {
                    for (int i=0;i<listOfBanks.length;i++) {
                        System.out.println(i + " - " + listOfBanks[i].getBankName());
                    }
                    bankNum = userInp.nextInt();
                    if (0 <= bankNum && bankNum < listOfBanks.length) {
                        bankChoice = listOfBanks[bankNum];
                    } else {
                        System.out.println("Invalid choice");
                    }
                }
                System.out.print("Enter first name: ");
                String f = userInp.next();
                System.out.print("Enter last name: ");
                String l = userInp.next();
                System.out.print("Enter opening deposit: ");
                double openingDepo = userInp.nextDouble();
                System.out.print("Create 6-digit PIN: ");
                int newPIN = userInp.nextInt();
                System.out.print("Confirm PIN: ");
                int confirmPIN = userInp.nextInt();
                if (newPIN == confirmPIN) {
                    System.out.println("PIN successfully created");
                } else {
                    System.out.println("PIN did not match. Account creation failed");
                    continue;
                }
                Account newAcc = new Account(openingDepo);
                bankChoice.addCustomer(f, l);
                newAcc.setPIN(newPIN);
                bankChoice.getCustomer(bankChoice.getNumOfCustomers()-1).setAccount(newAcc);
            } else if (choice == 2) {
                intendedAcc accLocInfo = bankSelect(listOfBanks);
                System.out.print("Enter deposit: ");
                double deposit = userInp.nextInt();
                boolean isAuthentic = PINAuthentication(accLocInfo);
                if (deposit < 50) {
                    System.out.println("Transaction failed. Deposit is less than $50");
                } else if (!isAuthentic) {
                    continue;
                } else {
                    accLocInfo.bankChoice.getCustomer(accLocInfo.customerNum).getAccount().deposit(deposit);
                    System.out.println("$" + deposit + " deposited successfully");
                }
            } else if (choice == 3) {
                intendedAcc accLocInfo = bankSelect(listOfBanks);
                System.out.print("Amount of money to withdraw: ");
                double withdrawal = userInp.nextDouble();
                boolean isAuthentic = PINAuthentication(accLocInfo);
                if (withdrawal > 500) {
                    System.out.println("Transaction failed. Withdrawal exceeded $499 at a time");
                } else if (!isAuthentic) {
                    continue;
                } else {
                    boolean success = accLocInfo.bankChoice.getCustomer(accLocInfo.customerNum).getAccount().withdraw(withdrawal);
                    if (success) {
                        System.out.println("$"+withdrawal+" withdrawn successfully");
                    } else {
                        System.out.println("Balance insufficient");
                    }
                }
            } else if (choice == 4) {
                System.out.println("(Donor's account)");
                intendedAcc donorAcc = bankSelect(listOfBanks);
                System.out.println("(Recipient's account)");
                intendedAcc recpntAcc = bankSelect(listOfBanks);
                System.out.print("Amount of money to transfer: ");
                double transferMoney = userInp.nextDouble();
                boolean isAuthentic = PINAuthentication(donorAcc);
                if (transferMoney > 500) {
                    System.out.println("Transaction failed. Withdrawal exceeded $499 at a time");
                } else if (!isAuthentic) {
                    continue;
                } else {
                    boolean success = donorAcc.bankChoice.getCustomer(donorAcc.customerNum).getAccount().withdraw(transferMoney);
                    if (success) {
                        System.out.println("$"+transferMoney+" transferred successfully");
                    } else {
                        System.out.println("Balance insufficient");
                    }
                    recpntAcc.bankChoice.getCustomer(recpntAcc.customerNum).getAccount().deposit(transferMoney);
                }
            } else if (choice == 5) {
                intendedAcc accLocInfo = bankSelect(listOfBanks);
                boolean isAuthentic = PINAuthentication(accLocInfo);
                if (isAuthentic) {
                    System.out.println(accLocInfo.bankChoice.getCustomer(accLocInfo.bankChoice.getNumOfCustomers()-1).toString());
                } else {
                    continue;
                }
            }
            else if (choice == 6) {
                break;
            }
        }
    }
}
