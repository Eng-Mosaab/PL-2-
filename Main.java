import java.util.*;

public class Main {

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.print("please enter you id: ");
        String sId = input.nextLine();
        System.out.print("enter the exam id: ");
        String eId = input.nextLine();

        StudentServices.examEntry(sId,eId);
        System.out.println("***********************");
        StudentServices.requestRecorrection(sId,eId);
    }
}