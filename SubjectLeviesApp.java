
import java.text.DecimalFormat;
import java.util.Scanner;


public class SubjectLeviesApp {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("0.00");
        
        String[] subjectCode = {"CFA115D","COH115D","PPA115D","WEB115D","PPB115D","DCT115D","CFB115D"};
        double[] oldLevies = {323.95, 100.05, 625.75, 380.95, 850.55,122.55 , 200.99};
        double[] newLevies = new double[oldLevies.length];
        String[] levyStatus = new String[oldLevies.length];
        
        String newStr = subjectCode[2].substring(2, 3).toUpperCase();
        subjectCode[2].replaceAll(subjectCode[2], newStr);
         
        displayLevyDetails(subjectCode, oldLevies, newLevies, levyStatus);
        System.out.println();
        
        char userInput = Character.toUpperCase(getUserOption());
        
        while(userInput != 'E'){
            
            switch(userInput){
                case 'C':
                    populateLevies(newLevies, subjectCode, oldLevies);
                    determineLevyStatus(levyStatus, newLevies, oldLevies);
                    displayLevyDetails(subjectCode, oldLevies, newLevies, levyStatus);
                    break;
                case 'S':
                    System.out.print("Search for levy - Enter the subject code: ");
                    String search = scanner.next();
                    
                    int index = searchSubjectLevy(subjectCode, search);
                    
                    if(index == -1){
                        System.out.println(search + " is an invalid subject Code");
                    }else{
                        System.out.println("Subject Code " + subjectCode[index] + " Status: " + levyStatus[index]);
                    }
                    break;
                
                    default:
                        System.out.println("Invalid option");
                        break;      
            }
            
            userInput = getUserOption();
        }
    }
    
    public static void populateLevies(double[] newLevies, String[] subjectCode, double[] oldLevies){
        Scanner scanner = new Scanner(System.in);
        
        for (int i = 0; i < newLevies.length; i++) {
            
            System.out.print("Enter new levies for " + subjectCode[i] + ": ");
            double newLevy = scanner.nextDouble();
            
            boolean check = validateLevies(newLevy, oldLevies[i]);
            
            if(check){
                
                newLevies[i] = newLevy;
                
            }else if(check == false){
                System.out.print("Invalid levy amount entered, must not be negative or less than the current levy \n");
                i--;
            }
        }
    }
    
    public static boolean validateLevies(double newLevy, double oldLevy){
        boolean output = false;
        
        if(newLevy > 0 && newLevy > oldLevy){
            
            output = true;
        }
        
        return output;
    }
    
    public static char getUserOption(){
        Scanner scanner = new Scanner(System.in);
        char output = ' ';
        System.out.println("Select any option to start: ");
        System.out.println("\t\tEnter C– Determine Levy Status."
                + "\n\t\tEnter S – Search Subject Levy."
                + "\n\t\tEnter E – Exit.");
        output = scanner.next().charAt(0);
        
        return output;
    }
    
    public static void determineLevyStatus(String[] levyStatus,double [] newLevies, double[] oldLevies){
        double perc = 0;
        DecimalFormat df = new DecimalFormat("0.0");
        
        for (int i = 0; i < oldLevies.length; i++) {
            double differece = newLevies[i] - oldLevies[i];
            double percIncrease = differece / oldLevies[i];
            perc = percIncrease * 100;
            
            if(perc <= 6.5){
                levyStatus[i] = "Approved "+ df.format(perc) + " increase % is not more than inflation";
            }else if(perc > 6.5){
                levyStatus[i] = "Not approved "+ df.format(perc) + "% increase is more than inflation";
            }else if(newLevies[i] < oldLevies[i]){
                levyStatus[i] = "Approved "+ df.format(perc) + "% less than previous levy";
            }else if(newLevies[i] == oldLevies[i]){
                levyStatus[i] = "Approved "+ df.format(perc) + "% levy stays the same";
            }  
        }
    }
    
    public static int searchSubjectLevy(String[] subjectCodes, String search){
     int index = -1;

            for (int i = 0; i < subjectCodes.length; i++) {
                if(subjectCodes[i].equalsIgnoreCase(search)){
                    index = i;
                    break;
                }
        }
        return index;
    }
    
    public static void displayLevyDetails(String[] subjectCodes, double[] oldLevies, double[] newLevies, String[] levyStatus){
        
        System.out.println("\t\t\t**********Sosh College Levy System***********");
        System.out.println("\t\t*************************************************************");
        System.out.println("Subject Code \t\t Old Levies \t\t New Levies \t\t Levy Status");
        
        for (int i = 0; i < levyStatus.length; i++) {
            System.out.println(subjectCodes[i] + " \t\t " + oldLevies[i] + " \t\t " + newLevies[i] + " \t\t\t " + levyStatus[i]);
        }
    }
}