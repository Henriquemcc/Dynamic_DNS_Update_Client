#!/bin/bash

# Folder where dynamic dns update client will be installed.
installation_directory=""

# Url app.jar file.
app_jar_file_url="https://github.com/Henriquemcc/Dynamic_DNS_Update_Client/releases/latest/download/app.jar"

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

# Installs systemd dynamic-dns-update-client service.
function install_systemd_service() {

  bash ./Install-Systemd.sh

  # Directory which systemd stores it's services.
  systemd_service_directory="/etc/systemd/system"

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
    echo "ExecStart=${installation_directory}/service.sh"
    echo ""
    echo "[Install]"
    echo "WantedBy=multi-user.target"
  } >${systemd_service_directory}/dynamic-dns-update-client.service

  # Enabling system service
  systemctl enable --now dynamic-dns-update-client.service

  # Starting system service
  systemctl start --now dynamic-dns-update-client.service
}

# Installs dynamic dns update client and it's dependencies on linux.
function install_on_linux() {

  # Setting installation directory
  installation_directory="/usr/share/dynamic-dns-update-client"

  # Installing java
  bash ./Install-Java.sh

  # Creating installation directory
  mkdir -p "$installation_directory"

  # Downloading jar file
  curl -L $app_jar_file_url --output "${installation_directory}/app.jar"

  # Changing jar file permission
  chmod 755 "${installation_directory}/app.jar"

  # Creating dduc.sh file
  {
    echo "#!/bin/bash"
    echo "java -jar ${installation_directory}/app.jar \$@"
  } >"${installation_directory}/dduc.sh"

  # Changing dduc.sh file permission
  chmod 755 "${installation_directory}/dduc.sh"

  # Defining the shortcut folder
  shortcut_folder="/usr/share/applications/"

  # Creating shortcut folder
  mkdir -p "$shortcut_folder"

  # Defining the shortcut file
  shortcut_file="${shortcut_folder}/dynamic-dns-update-client.desktop"

  # Creating shortcut
  {
    echo "[Desktop Entry]"
    echo "Version=1.5"
    echo "Name=Dynamic Dns Update Client"
    echo "Type=Application"
    echo "Exec=${installation_directory}/dduc.sh"
    echo "StartupNotify=true"
    echo "Categories=Network;"
  } > "$shortcut_file"

  # Creating service.sh file
  {
    echo "#!/bin/bash"
    echo "java -jar ${installation_directory}/app.jar perform-ip-update"
    echo "java -jar ${installation_directory}/app.jar perform-ip-update -i"
  } >"${installation_directory}/service.sh"

  # Changing service.sh file permission
  chmod 755 "${installation_directory}/service.sh"

  # Creating symbolic link on the /bin folder
  ln --symbolic "${installation_directory}/dduc.sh" "/bin/dduc"

  # Installing systemd service
  install_systemd_service
}

# Forcing to run as root
run_as_root

# Identifying operating system
if ! [ "$(uname)" == "Linux" ]; then
  echo "This operating system is NOT a Linux distribution!"
  exit 1
fi

install_on_linux
