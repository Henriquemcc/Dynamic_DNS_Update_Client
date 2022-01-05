#!/bin/bash

# Runs this script as root if it is not root.
function run_as_root() {
  if [ "$(whoami)" != "root" ]; then
    echo "This script is not running as root"
    if [ "$(command -v sudo)" ]; then
      echo "Elevating privileges..."
      sudo bash "$0"
      exit $?
    else
      echo "Sudo is not installed"
      exit 1
    fi
  fi
}

function uninstall_on_linux() {

  # Setting installation directory
  installation_directory="/usr/share/dynamic-dns-update-client"

  # Removing installation directory
  rm -rf "$installation_directory"

  # Defining the shortcut folder
  shortcut_folder="/usr/share/applications/"

  # Defining the shortcut file
  shortcut_file="${shortcut_folder}/dynamic-dns-update-client.desktop"

  # Removing shortcut
  rm "$shortcut_file"

  # Directory which systemd stores it's services.
  systemd_service_directory="/etc/systemd/system"

  # Removing system service
  rm "${systemd_service_directory}/dynamic-dns-update-client.service"

  # Removing symbolic link
  rm "/bin/dduc"
}

# Forcing to run as root
run_as_root

# Identifying operating system and calling the specific function.
if [ "$(uname)" == "Linux" ]; then
  uninstall_on_linux
else
  exit 1
fi
