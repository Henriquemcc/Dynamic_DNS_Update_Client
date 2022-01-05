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

function uninstall_dynamic_dns_update_client() {

  uninstall_error=()

  # Setting installation directory
  installation_directory="/usr/share/dynamic-dns-update-client"

  # Removing installation directory
  if [ -d $installation_directory ]; then
    rm -rf "$installation_directory" || uninstall_error+=(1)
  fi

  # Defining the shortcut folder
  shortcut_folder="/usr/share/applications/"

  # Defining the shortcut file
  shortcut_file="${shortcut_folder}/dynamic-dns-update-client.desktop"

  # Removing shortcut
  if [ -f $shortcut_file ]; then
    rm "$shortcut_file" || uninstall_error+=(2)
  fi

  # Directory which systemd stores it's services.
  systemd_service_directory="/etc/systemd/system"

  # Removing system service
  system_service_path="${systemd_service_directory}/dynamic-dns-update-client.service"
  if [ -f $system_service_path ]; then
    rm $system_service_path || uninstall_error+=(3)
  fi

  # Removing symbolic link
  symbolic_link_path="/bin/dduc"
  if [ -L $symbolic_link_path ]; then
    rm $symbolic_link_path || uninstall_error+=(4)
  fi

  # Printing error message
  if [ ${#uninstall_error[@]} -ge 1 ]; then
      echo "An error occurred during the uninstall:" "${uninstall_error[@]}"
      exit 1
  fi

  echo "Dynamic DNS Update Client successfully uninstalled"
}

# Forcing to run as root
run_as_root

# Identifying operating system
if ! [ "$(uname)" == "Linux" ]; then
  echo "This operating system is NOT a Linux distribution!"
  exit 1
fi

echo "Are you sure you want to uninstall Dynamic DNS Update Client? (yes/no)"
read -r answer

if ! [ "$answer" == "yes" ]; then
    exit 0
fi

uninstall_dynamic_dns_update_client