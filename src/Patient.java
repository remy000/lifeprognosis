import java.time.LocalDate;

public class Patient extends User {
    private String dateOfBirth;
    private String hivInfected;
    private String diagnosisDate;
    private String artDrugs;
    private String startDate;
    private String countryIso;

    public Patient() {
    }

    public Patient(String firstName, String lastName, String email, String password, String role) {
        super(firstName, lastName, email, password, role);
    }

    public Patient(String firstName, String lastName, String email, String password, String role, String dateOfBirth, String hivInfected, String diagnosisDate, String artDrugs, String startDate, String countryIso) {
        super(firstName, lastName, email, password, role);
        this.dateOfBirth = dateOfBirth;
        this.hivInfected = hivInfected;
        this.diagnosisDate = diagnosisDate;
        this.artDrugs = artDrugs;
        this.startDate = startDate;
        this.countryIso = countryIso;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHivInfected() {
        return hivInfected;
    }

    public void setHivInfected(String hivInfected) {
        this.hivInfected = hivInfected;
    }

    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(String diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getArtDrugs() {
        return artDrugs;
    }

    public void setArtDrugs(String artDrugs) {
        this.artDrugs = artDrugs;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCountryIso() {
        return countryIso;
    }

    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
    }

    public void viewProfile(){

    }
    public void updateProfile(){

    }

    public int calculateLifeSpan(){
        return 0;
    }

}
