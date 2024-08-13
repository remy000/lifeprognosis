#!/bin/bash
USER_STORE="src/userstore.txt"
function hashPassword() {
  echo -n "$1" | openssl dgst -sha256 | awk '{print $2}'
}
function initiateRegistration() {
     local email="$1"

         # Check if the email already exists in the userstore
         if grep -q "$email" "$USER_STORE"; then
             echo "The email $email is already taken."
             exit 1
         else
             # Proceed with the registration process
             uuid=$(uuidgen)
                  role="PATIENT"
                 echo "$email,$uuid,$role" >> $USER_STORE
                 echo "registration initiation completed. UUID: $uuid"
         fi

}

function completeRegistration() {
   local uuid="$1"
      local firstName="$2"
      local lastName="$3"
      local dateOfBirth="$4"
      local hivPositive="$5"
       local onArtDrugs="$6"
       local countryIso="$7"
        local password="$8"
      local diagnosisDate="$9"
      local artStartDate="${10}"
      hashedPassword=$(hashPassword "$password")
  if ! grep -q "$uuid" $USER_STORE; then
    echo "Invalid UUID"
    return
  fi
   sed -i "/$uuid/s/$/,${firstName},${lastName},${dateOfBirth},${hivPositive},${diagnosisDate},${onArtDrugs},${artStartDate},${countryIso},${hashedPassword}/" $USER_STORE
   echo "registration completed."


}
function login(){
  local email="$1"
      local password="$2"

       hashedPassword=$(hashPassword "$password")

      if grep -q "$email" $USER_STORE; then
          userData=$(grep "$email" $USER_STORE)
           firstName=$(echo "$userData" | cut -d, -f4)
            lastName=$(echo "$userData" | cut -d, -f5)
            role=$(echo "$userData" | cut -d, -f3)
          storedPassword=$(echo "$userData" | cut -d, -f12)
          if [[ "$hashedPassword" == "$storedPassword" ]]; then
              echo "Login successful,$firstName,$lastName,$role"
          else
              echo "Invalid credentials"
          fi
      else
          echo "User not found"
      fi

}
function viewProfile() {
    local email="$1"
       if grep -q "$email" "$USER_STORE"; then
           userData=$(grep "$email" "$USER_STORE")
           uuid=$(echo "$userData" | cut -d, -f2)
           firstName=$(echo "$userData" | cut -d, -f4)
           lastName=$(echo "$userData" | cut -d, -f5)
           dateOfBirth=$(echo "$userData" | cut -d, -f6)
           hivPositive=$(echo "$userData" | cut -d, -f7)
           diagnosisDate=$(echo "$userData" | cut -d, -f8)
           onArtDrugs=$(echo "$userData" | cut -d, -f9)
           artStartDate=$(echo "$userData" | cut -d, -f10)
           countryIso=$(echo "$userData" | cut -d, -f11)

           echo "UUID: $uuid"
           echo "Names: $firstName $lastName"
           echo "Email: $email"
           echo "Birth Date: $dateOfBirth"
           echo "HIV Infected: $hivPositive"

           if [ "$hivPositive" == "yes" ]; then
               echo "Diagnosis Date: $diagnosisDate"
               echo "Art Drugs: $onArtDrugs"
               echo "Start Date: $artStartDate"
           fi

           echo "Country Iso: $countryIso"
       else
           echo "User not found"
       fi
}
function updateProfile() {
    local email="$1"
    local numOfColumns="$2"
    shift 2

    if grep -q "$email" "$USER_STORE"; then
        userData=$(grep "$email" "$USER_STORE")
        IFS=',' read -ra parts <<< "$userData"

        for ((i = 0; i < numOfColumns; i++)); do
            local column="$1"
            local newValue="$2"
            shift 2
            case "$column" in
                "firstName") parts[3]="$newValue" ;;
                "lastName") parts[4]="$newValue" ;;
                "dateOfBirth") parts[5]="$newValue" ;;
                "hivPositive") parts[6]="$newValue" ;;
                "diagnosisDate") parts[7]="$newValue" ;;
                "onArtDrugs") parts[8]="$newValue" ;;
                "artStartDate") parts[9]="$newValue" ;;
                "countryIso") parts[10]="$newValue" ;;
                "password") parts[11]=$(hashPassword "$newValue") ;;
                *) echo "Invalid column: $column" ;;
            esac
        done

        newUserData=$(IFS=','; echo "${parts[*]}")
        sed -i "/$email/c\\$newUserData" "$USER_STORE"
        echo "Profile updated successfully"
    else
        echo "User not found"
    fi
}


case $1 in
    "initiateRegistration")
        initiateRegistration $2
        ;;
    "completeRegistration")
    shift
        completeRegistration "$@"
        ;;
    "login")
        login $2 $3
        ;;
    "viewProfile")
              viewProfile "$2"
               ;;

    "updateProfile")
    shift
           updateProfile "$@"
           ;;
    *)
        echo "Usage: $0 {initiateRegistration|completeRegistration|login|viewProfile|updateProfile}"
        ;;
esac
