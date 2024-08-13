#!/bin/bash
USER_STORE="src/userstore.txt"
LIFE_EXPECTANCY_FILE="src/life-expectancy.csv"
function getLifeExpectancy() {
    local countryIso="$1"
    lifeExpectancy=$(grep "$countryIso," "$LIFE_EXPECTANCY_FILE" | cut -d, -f7)
     lifeExpectancy=${lifeExpectancy%%.*}
    echo "$lifeExpectancy"

}
function calculateLifespan() {
    local email="$1"

    if grep -q "$email" "$USER_STORE"; then
        userData=$(grep "$email" "$USER_STORE")
        birthDate=$(echo "$userData" | cut -d, -f6)
        hivPositive=$(echo "$userData" | cut -d, -f7)
        diagnosisDate=$(echo "$userData" | cut -d, -f8)
        onArtDrugs=$(echo "$userData" | cut -d, -f9)
        artStartDate=$(echo "$userData" | cut -d, -f10)
        countryIso=$(echo "$userData" | cut -d, -f11)

        birthYear=$(date -d "$birthDate" +"%Y")
        diagnosisYear=$(date -d "$diagnosisDate" +"%Y")
        artStartYear=$(date -d "$artStartDate" +"%Y")
        currentYear=$(date +"%Y")

        patientAge=$((diagnosisYear - birthYear))
        yearsSinceDiagnosis=$((currentYear - diagnosisYear))
        lifeExpectancy=$(getLifeExpectancy "$countryIso")
        remainingYears=$((lifeExpectancy - patientAge))

        if [[ "$hivPositive" == "yes" ]]; then
            # Calculate the remaining lifespan if the patient is HIV positive
            if [[ "$onArtDrugs" == "yes" ]]; then
                delayYears=$((artStartYear - diagnosisYear))
               local adjustedLifespan=$remainingYears
                          for ((i = 0; i < delayYears; i++)); do
                             adjustedLifespan=$((adjustedLifespan * 90 / 100))
                          done
                echo "Estimated lifespan: $adjustedLifespan years"
            else
                yearsLeft=$((5 - yearsSinceDiagnosis))
                if [[ $yearsLeft -lt 0 ]]; then
                    yearsLeft=0
                fi
                echo "Estimated lifespan: $yearsLeft years (not on ART drugs)"
            fi
        else


            echo "Estimated lifespan: $remainingYears years (not HIV positive)"
        fi
    else
        echo "User not found"
    fi
}
case "$1" in
    getLifeExpectancy)
        getLifeExpectancy "$2"
        ;;
    calculateLifespan)
        calculateLifespan "$2"
        ;;
    *)
        echo "Invalid command. Use getLifeExpectancy <countryIso> or calculateLifespan <email>"
        ;;
esac