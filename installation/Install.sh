#!/bin/bash

repository_zip_url="https://github.com/Henriquemcc/Dynamic_DNS_Update_Client/archive/refs/heads/main.zip"
destination_directory="/tmp"

function download_repository()
{
  # Creating destination directory
  if ! [ -d $destination_directory ]; then
      sudo mkdir -p $destination_directory
  fi

  # Defining destination file path
  destination_file_path="${destination_directory}/Dynamic_DNS_Update_Client.zip"

  # Downloading file
  curl -L $repository_zip_url --output $destination_file_path

  # Returning destination file path
  echo $destination_file_path
}


function install_on_Linux()
{
  # Downloading repository
  file_path="$(download_repository)"


  # Storing path
  old_path="$(pwd)"

  # Changing directory
  cd "$destination_directory" || exit 1

  # Unzipping file
  unzip -o "$file_path" || exit 1

  # Changing directory
  cd "${destination_directory}/Dynamic_DNS_Update_Client-main/installation/Linux" || exit 1

  # Installing
  bash ./Install.sh

  # Returning to the old path
  cd "$old_path" || exit 1
}

if [ "$(uname)" == "Linux" ]; then
  install_on_Linux
fi