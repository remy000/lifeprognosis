import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        //calling bash to log in a user
        return executeBashCommand("bash","src/user_manager.sh", "login", email, password);
    }
    public static void downloadUserStore(String email) throws Exception {
        executeBashCommand("bash", "src/report.sh", "exportUserData", email);
    }
    public static void downloadPatientCalendar(String email) throws Exception {
        executeBashCommand("bash", "src/report.sh", "exportCalendar", email);
    }
    public static void viewProfile(String email) throws Exception {
         executeBashCommand("bash","src/user_manager.sh", "viewProfile", email);
    }
     public static void calculateLifeSpan(String email) throws Exception{
         executeBashCommand("bash","src/lifeSpan.sh", "calculateLifespan", email);
     }

     public static void updateProfile(String email) throws Exception {
        Scanner scanner = new Scanner(System.in);
    
        String[] allColumns = {"firstName", "lastName", "dateOfBirth", "hivPositive", "diagnosisDate",
                "onArtDrugs", "artStartDate", "countryIso", "password"};
        
        boolean moreUpdates = true;
        
        while (moreUpdates) {
            System.out.println("Available fields for update:");
            System.out.println("****************************\n");
            for (int i = 0; i < allColumns.length; i++) {
                System.out.println((i + 1) + ". " + allColumns[i]);
            }
    
            System.out.print("choose the field you want to update: ");
            int columnChoice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline
    
            if (columnChoice < 1 || columnChoice > allColumns.length) {
                System.out.println("Invalid choice. Please select a valid field number.");
                continue;
            }
    
            String columnToUpdate = allColumns[columnChoice - 1];
            System.out.print("Enter new value for " + columnToUpdate + ": ");
            String newValue = scanner.nextLine();
    
            String[] args = new String[7];
            args[0] = "bash";
            args[1] = "src/user_manager.sh";
            args[2] = "updateProfile";
            args[3] = email;
            args[4] = "1";  // We are updating one column at a time
            args[5] = columnToUpdate;
            args[6] = newValue;
    
            executeBashCommand(args);
    
            System.out.print("Do you want to update more field? (yes/no): ");
            String response = scanner.nextLine();
            moreUpdates = response.equalsIgnoreCase("yes");
        }
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

    public static void adminHome(String email){
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
                        String emails = scan.nextLine();
                        initiateRegistration(emails);
                        break;
                    case 2:
                        downloadUserStore(email);

                        break;
                    case 3:
                        System.out.println("Existing...");
                        run = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }


            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

    }

    public static void patientHome(String email){
        Scanner scan = new Scanner(System.in);
        boolean run = true;
        while (run) {
            System.out.println("\t\tPATIENT HOMEPAGE");
            System.out.println("\t=======================\n");

            System.out.println("1. View Profile Data");
            System.out.println("2. Update Profile Data");
            System.out.println("3. Calculate LifeSpan");
            System.out.println("4. Download iCalendar");
            System.out.println("5. Exit");

            int choice = scan.nextInt();
            scan.nextLine();
            try{
                switch (choice){
                    case 1:
                        System.out.print("\nPatient Data\n");
                        System.out.print("===============\n");
                        viewProfile(email);
                        break;
                    case 2:
                        updateProfile(email);
                        break;
                    case 3:
                        calculateLifeSpan(email);
                        break;
                    case 4:
                        downloadPatientCalendar(email);
                        break;
                    case 5:
                        System.out.println("Exiting the system...");
                        run = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.\n");
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
            System.out.println("\n\t\tWELCOME TO PATIENT PROGNOSIS");
            System.out.println("\t**************************************\n");
            System.out.println("1. Login");
            System.out.println("2. Complete Registration");

            System.out.println("3. exit");
            System.out.println("Choose an option: ");

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
                        char[] loginPasswordArray = System.console().readPassword();
                        String loginPassword = new String(loginPasswordArray);

                        String result=login(loginEmail, loginPassword);
                        String[] parts=result.split(",");
                        if(parts.length>1) {
                            if (parts[3].contains("PATIENT")) {
                                patientHome(loginEmail);
                            } else {
                                adminHome(loginEmail);
                            }
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
                        String dateOfBirth = "";
                        while (true) {
                            System.out.print("Date of Birth (YYYY-MM-DD): ");
                            dateOfBirth = scanner.nextLine();
                            if (isValidDate(dateOfBirth) && !isFutureDate(dateOfBirth)) {
                                break;
                            } else {
                                System.out.println("Invalid Date of Birth. It can't be in the future.");
                            }
                        }

                        System.out.print("HIV Positive (yes/no): ");
                        String hivPositive = scanner.nextLine();
                        String diagnosisDate = "", onArtDrugs = "no", artStartDate = "";
                        if (hivPositive.equalsIgnoreCase("yes")) {
                            while (true) {
                                System.out.print("Diagnosis Date (YYYY-MM-DD): ");
                                diagnosisDate = scanner.nextLine();
                                if (isValidDate(diagnosisDate) && !isFutureDate(diagnosisDate)) {
                                    break;
                                } else {
                                    System.out.println("Invalid Diagnosis Date. It can't be in the future.");
                                }
                            }

                            System.out.print("On ART Drugs (yes/no): ");
                            onArtDrugs = scanner.nextLine();
                            if (onArtDrugs.equalsIgnoreCase("yes")) {
                                while (true) {
                                    System.out.print("ART Start Date (YYYY-MM-DD): ");
                                    artStartDate = scanner.nextLine();
                                    if (isValidDate(artStartDate) && !isFutureDate(artStartDate) && isAfter(diagnosisDate, artStartDate)) {
                                        break;
                                    } else {
                                        System.out.println("Invalid ART Start Date. It must be after the Diagnosis Date and not in the future.");
                                    }
                                }
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
    private static boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Check if the date is in the future
    private static boolean isFutureDate(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return date.isAfter(LocalDate.now());
    }

    // Check if artStartDate is after diagnosisDate
    private static boolean isAfter(String diagnosisDateStr, String artStartDateStr) {
        LocalDate diagnosisDate = LocalDate.parse(diagnosisDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate artStartDate = LocalDate.parse(artStartDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return artStartDate.isAfter(diagnosisDate);
    }

}
