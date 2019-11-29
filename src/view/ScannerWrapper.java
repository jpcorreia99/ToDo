package view;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerWrapper {


    public  String readString(String errormesssage) {
        Scanner sc = new Scanner(System.in);
        boolean ok = false;
        String ret = "";
        while (!ok) {
            try {
                ret = sc.nextLine();
                ok = true;
            } catch (InputMismatchException e) {
                System.out.println(errormesssage + "\n");
            }
        }
        return ret;
    }


    public  int readInt(String errorMesssage, int lowerLimit, int upperLimit) {
        Scanner sc = new Scanner(System.in);
        boolean ok = false;
        int i = 0;
        while (!ok) {
            try {
                i = sc.nextInt();
                if (i < lowerLimit) {
                    System.out.println(errorMesssage);
                } else if (i > upperLimit) {
                    System.out.println(errorMesssage);
                } else {
                    ok = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("The inserted value is not an Integer\n");
                sc.nextLine(); //flushes the buffer;
            }
        }
        return i;
    }

    public  LocalDateTime readDate() {
        Scanner sc = new Scanner(System.in);
        Boolean ok = false;
        int year=0;
        int month=0;
        int day=0;
        int hour=0;
        int minute=0;
        while(!ok) {
            System.out.print("Year: ");
            year = readInt("Invalid year", LocalDateTime.now().getYear(), 2050);
            System.out.print("Month: ");
             month = readInt("Invalid month", 1, 12);
            System.out.print("Day: ");
            if (month == 2) {
                day = readInt("invalid Day ", 1, 28);
            } else if (((month <= 6) && (month % 2 == 0)) || ((month >= 9) && (month % 2 == 1))) {
                day = readInt("Invalid day ", 1, 30);
            } else {
                day = readInt("Invalid day ", 1, 31);
            }
            System.out.print("Hour: ");
            hour = readInt("Invalid hour", 0, 23);
            System.out.print("Minute: ");
            minute = readInt("Invalid minute", 0, 59);

            if (LocalDateTime.of(year, month, day, hour, minute, 0, 0).isAfter(LocalDateTime.now())){
                ok = true;
            }else{
                System.out.println("Invalid Date: can't set a date that's in the past.\n");
                System.out.println("Press ENTER to continue");
                sc.nextLine();
            }
        }

        return LocalDateTime.of(year, month, day, hour, minute, 0, 0);
    }


    public  void cleanScreen(){
        System.out.println(new String(new char[50]).replace("\0", "\r\n"));
    }
}
