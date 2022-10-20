#!/usr/bin/bash

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

# Running as root
run_as_root

# Directory which systemd stores it's services.
systemd_service_directory="/etc/systemd/system"

# Systemd Service Name
systemd_service_name="dynamic-dns-update-client.service"

# Starting system service
systemctl stop --now "$systemd_service_name"

# Disabling system service
systemctl disable --now "$systemd_service_name"

# Deleting system service
rm "${systemd_service_directory}/${systemd_service_name}"

# Removing jar file
rm "/usr/bin/Dynamic_Dns_Update_Client.jar"