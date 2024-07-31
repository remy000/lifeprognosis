#!/bin/bash
USER_STORE="userstore.txt"
function hashPassword() {
  echo -n "$1" | openssl dgst -sha256 | awk '{print $2}'
}
function initiateRegistration() {
     local email="$1"
      uuid=$(uuidgen)
      role="PATIENT"
     echo "$email,$uuid,$role" >> $USER_STORE
     echo "User initiation completed. UUID: $uuid"
}

function completeRegistration() {
   local uuid="$1"
      local firstname="$2"
      local lastname="$3"
      local dateOfBirth="$4"
      local hivPositive="$5"
      local diagnosisDate="$6"
      local onArtDrugs="$7"
      local artStartDate="$8"
      local countryIso="$9"
      local password="${10}"
       hashedPassword=$(hashPassword "$password")
  if ! grep -q "$uuid" $USER_STORE; then
    echo "Invalid UUID"
    exit 1
  fi


   sed -i "/$uuid/s/$/,${firstname},${lastname},${dateOfBirth},${hivPositive},${diagnosisDate},${onArtDrugs},${artStartDate},${countryIso},${hashedPassword}/" $USER_STORE
   echo "registration completed."
}
function login(){
  local email="$1"
      local password="$2"
       hashedPassword=$(hashPassword "$password")

      if grep -q "$email" $USER_STORE; then
          userData=$(grep "$email" $USER_STORE)
          storedPassword=$(echo "$userData" | cut -d, -f11)
          if [[ "$hashedPassword" == "$storedPassword" ]]; then
              echo "Login successful,${userData}"
          else
              echo "Invalid credentials"
          fi
      else
          echo "User not found"
      fi

}
case $1 in
    "initiateRegistration")
        initiateRegistration $2
        ;;
    "completeRegistration")
        completeRegistration $2 $3 $4 $5 $6 $7 $8 $9 ${10}
        ;;
    "login")
        login $2 $3
        ;;
    *)
        echo "Usage: $0 {initiateRegistration|completeRegistration|login}"
        ;;
esac
