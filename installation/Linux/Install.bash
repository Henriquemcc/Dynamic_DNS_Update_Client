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

# Identifying operating system
if ! [ "$(uname)" == "Linux" ]; then
  echo "This operating system is NOT a Linux distribution!"
  exit 1
fi

run_as_root

# Installing requirements
bash ./Install-Java.bash
bash ./Install-Systemd.bash

if ! [ "$(command -v wget)" ] && ! [ "$(command -v curl)" ]; then
  bash ./Install-Curl.bash
fi

# Declaring the source url and the destination path of the executable jar file
jar_source_file_url="https://github.com/Henriquemcc/Dynamic_DNS_Update_Client/releases/latest/download/app.jar"
jar_destination_file_path="/usr/bin/Dynamic_Dns_Update_Client.jar"

# Creating java download destination directory
mkdir -p "$(dirname "$jar_destination_file_path")"

# Downloading Jar File
if [ "$(command -v curl)" ]; then
  curl -o "$jar_destination_file_path" "$jar_source_file_url"
elif [ "$(command -v wget)" ]; then
  wget -O "$jar_destination_file_path" "$jar_source_file_url"
else
  exit 1
fi

# Directory which systemd stores it's services.
systemd_service_directory="/etc/systemd/system"

# Systemd Service Name
systemd_service_name="dynamic-dns-update-client.service"

# Java Executable File Path
java_executable_path="$(readlink -f "$(which java)")"

# Creating system service file
{
  echo "[Unit]"
  echo "Description=Dynamic dns update client service"
  echo "After=network.target"
  echo "StartLimitIntervalSec=0"
  echo ""
  echo "[Service]"
  echo "Type=simple"
  echo "Restart=always"
  echo "RestartSec=1"
  echo "User=root"
  echo "ExecStart=${java_executable_path} -jar ${jar_destination_file_path} perform-ip-update"
  echo ""
  echo "[Install]"
  echo "WantedBy=multi-user.target"
} >"${systemd_service_directory}/${systemd_service_name}"

# Enabling system service
systemctl enable --now "$systemd_service_name"

# Starting system service
systemctl start --now "$systemd_service_name"