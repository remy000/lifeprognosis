#!/bin/bash
USER_STORE="userstore.txt"
function hashPassword() {
  echo -n "$1" | openssl dgst -sha256 | awk '{print $2}'
}
function initiateRegistration() {
  read -p "Enter user email: " email
  uuid=$(uuidgen)
  role="PATIENT"
  echo "$email,$uuid,$role" >> $USER_STORE
  echo "user registration initiated. UUID: $uuid"
}

function completeRegistration() {
  read -p "Enter UUID: " uuid

  if ! grep -q "$uuid" $USER_STORE; then
    echo "Invalid UUID"
    exit 1
  fi
  read -p "Enter firstName:  " firstname
  read -p "Enter lastName: " lastname
  read -p "Enter Date of Birth (YYYY-MM-DD): " dateofBirth
  read -p "HIV Positive (yes/no): " hivPositive
   if [[ "$hivPositive" == "yes" ]]; then
          read -p "Enter Diagnosis Date (YYYY-MM-DD): " diagnosisDate
          read -p "On ART Drugs (yes/no): " onArtDrugs
          if [[ "$onArtDrugs" == "yes" ]]; then
              read -p "Enter ART Start Date (YYYY-MM-DD): " artStartDate
          else
              artStartDate=""
          fi
      else
          diagnosisDate=""
          onArtDrugs="no"
          artStartDate=""
      fi
  read -p "Enter country residence (ISO CODE): " countryIso
  read -p "Enter password: " password
  hashedPassword=$(hashPassword "$password")
   sed -i "/$uuid/s/$/,${firstname},${lastname},${dateofBirth},${hivPositive},${diagnosisDate},${onArtDrugs},${artStartDate},${countryIso},${hashedPassword}/" $USER_STORE
   echo "registration completed."
}
