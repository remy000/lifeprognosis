import java.time.LocalDate;

public class Patient extends User {
    private LocalDate dateOfBirth;
    private boolean hivInfected;
    private LocalDate diagnosisDate;
    private boolean artDrugs;
    private LocalDate startDate;
    private String countryIso;

    public Patient(String firstName, String lastName, String email, String password, String role) {
        super(firstName, lastName, email, password, role);
    }

    public Patient(LocalDate dateOfBirth, boolean hivInfected, LocalDate diagnosisDate, boolean artDrugs, LocalDate startDate, String countryIso) {
        this.dateOfBirth = dateOfBirth;
        this.hivInfected = hivInfected;
        this.diagnosisDate = diagnosisDate;
        this.artDrugs = artDrugs;
        this.startDate = startDate;
        this.countryIso = countryIso;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isHivInfected() {
        return hivInfected;
    }

    public void setHivInfected(boolean hivInfected) {
        this.hivInfected = hivInfected;
    }

    public LocalDate getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(LocalDate diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public boolean isArtDrugs() {
        return artDrugs;
    }

    public void setArtDrugs(boolean artDrugs) {
        this.artDrugs = artDrugs;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
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
