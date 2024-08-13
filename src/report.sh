#!/bin/bash
USER_STORE="src/userstore.txt"
OUTPUT_FILE="src/patient_data.csv"

function exportUserData() {
    local email="$1"
    # Check if the role is "ADMIN"
     if grep -q "$email" "$USER_STORE"; then
       userData=$(grep "$email" "$USER_STORE")
       role=$(echo "$userData" | cut -d, -f3)
       if [[ "$role" == "ADMIN" ]]; then
        echo "Exporting patient data..."
         if [ -f "$OUTPUT_FILE" ] && [ ! -w "$OUTPUT_FILE" ]; then
                        echo "Error: No write permission for $OUTPUT_FILE."
                        return 1
                    fi
        echo "email,UUID,Role,FirstName,LastName,DOB,HIVStatus,DiagnosisDate,OnART,ARTStartDate,CountryISO" > "$OUTPUT_FILE"
          awk -F, '
                    $3 == "PATIENT" {
                        OFS=","
                        if ($8 == "") { $8 = "N/A" }
                        if ($10 == "") { $10 = "N/A" }
                        print $1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11
                    }
                ' "$USER_STORE" >> "$OUTPUT_FILE"

        echo "Patient data exported successfully"
    else
        echo "Access denied: Only admin can export user data."
    fi
    else
      echo "Access denied: Only admin can export user data."
    fi
}

# Call the function with the provided email
exportUserData "$2"
