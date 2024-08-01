import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    //initiating registration
    public static void initiateRegistration(String email) throws Exception {
        //calling bash to initiate registration
         executeBashCommand("bash","src/user_manager.sh", "initiateRegistration", email);
    }
    // complete registration
    public static  void completeRegistration(String uuid,Patient patient) throws Exception {
        //calling bash to complete registration for patient
         executeBashCommand("bash","src/user_manager.sh", "completeRegistration",uuid, patient.getFirstName(), patient.getLastName(), patient.getDateOfBirth(),
                patient.getHivInfected(),patient.getArtDrugs(),patient.getCountryIso(),patient.getPassword(), patient.getDiagnosisDate(),patient.getStartDate()) ;

    }
    //login
    public static String login(String email, String password) throws Exception {
        //calling bash to login a user
        return executeBashCommand("bash","src/user_manager.sh", "login", email, password);
    }

    //function to execute commands for running bash
    private static String executeBashCommand(String... args) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(args);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        process.waitFor();
        // returning the response from bash
        System.out.println(output.toString());
        return output.toString();
    }

    public static void adminHome(){
        Scanner scan = new Scanner(System.in);
        boolean run = true;
        while (run) {
            System.out.println("ADMIN HOMEPAGE");
            System.out.println("\n============================\n");
            System.out.println("Choose an option: ");
            System.out.println("1. Initiate Registration");
            System.out.println("2. Download Document");
            System.out.println("3. Exit");

            int choice = scan.nextInt();
            scan.nextLine();
            try{
                switch (choice){
                    case 1:
                        System.out.print("Enter user email: ");
                        String email = scan.nextLine();
                        initiateRegistration(email);
                        break;
                    case 2:
                        System.out.println("Downloading...");
                        break;
                    case 3:
                        System.out.println("Existing...");
                        run = false;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }


            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

    }
   //main
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("WELCOME TO PATIENT PROGNOSIS");
            System.out.println("\n============================\n\n");
            System.out.println("Choose an option: ");
            System.out.println("1. Login");
            System.out.println("2. Complete Registration");

            System.out.println("3. exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                //switch case for performing different functionalities
                switch (choice) {
                   //login

                    case 1:
                        System.out.print("Enter email: ");
                        String loginEmail = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String loginPassword = scanner.nextLine();
                        String result=login(loginEmail, loginPassword);
                        if (result.contains("ADMIN")) {
                            adminHome();
                        } else {
                            System.out.println(result);
                        }
                        break;
                        //completing registration
                    case 2:
                        System.out.print("Enter UUID: ");
                        String uuid = scanner.nextLine();
                        System.out.print("First Name: ");
                        String firstName = scanner.nextLine();
                        System.out.print("Last Name: ");
                        String lastName = scanner.nextLine();
                        System.out.print("Date of Birth (YYYY-MM-DD): ");
                        String dateOfBirth = scanner.nextLine();
                        System.out.print("HIV Positive (yes/no): ");
                        String hivPositive = scanner.nextLine();
                        String diagnosisDate = "", onArtDrugs = "no", artStartDate = "";
                        if (hivPositive.equalsIgnoreCase("yes")) {
                            System.out.print("Diagnosis Date (YYYY-MM-DD): ");
                            diagnosisDate = scanner.nextLine();
                            System.out.print("On ART Drugs (yes/no): ");
                            onArtDrugs = scanner.nextLine();
                            if (onArtDrugs.equalsIgnoreCase("yes")) {
                                System.out.print("ART Start Date (YYYY-MM-DD): ");
                                artStartDate = scanner.nextLine();
                            }
                        }
                        System.out.print("Country of Residence (ISO Code): ");
                        String countryIso = scanner.nextLine();
                        System.out.print("Password: ");
                        String password = scanner.next();
                        Patient patient = new Patient();
                        patient.setFirstName(firstName);
                        patient.setLastName(lastName);
                        patient.setDateOfBirth(dateOfBirth);
                        patient.setHivInfected(hivPositive);
                        patient.setDiagnosisDate(diagnosisDate);
                        patient.setArtDrugs(onArtDrugs);
                        patient.setStartDate(artStartDate);
                        patient.setCountryIso(countryIso);
                        patient.setPassword(password);
                        completeRegistration(uuid, patient);
                        break;
                        //login

                    case 3:
                        System.out.println("Exiting the program...");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

            scanner.close();
        }

}
