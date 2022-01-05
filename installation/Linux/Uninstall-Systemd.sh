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

# Uninstalls systemd on rpm distros.
function uninstall_systemd_on_rpm_distros() {
  dnf autoremove systemd
}

# Uninstalls systemd on deb distros.
function uninstall_systemd_on_deb_distros() {
  apt autoremove systemd
}

# Uninstalls systemd on archlinux distros.
function uninstall_systemd_on_archlinux_distros() {
  pacman -R systemd
}

# Uninstalls systemd on rpm-ostree distros.
function uninstall_systemd_on_rpm_os_tree_distros() {
  rpm-ostree uninstall systemd
}

function uninstall_on_linux() {
  if [ "$(command -v dnf)" ]; then
    uninstall_systemd_on_rpm_distros
  elif [ "$(command -v apt)" ]; then
    uninstall_systemd_on_deb_distros
  elif [ "$(command -v pacman)" ]; then
    uninstall_systemd_on_archlinux_distros
  elif [ "$(command -v rpm-ostree)" ]; then
    uninstall_systemd_on_rpm_os_tree_distros
  fi
}

# Checking if systemd is installed
if ! [ "$(command -v systemctl)" ]; then
    exit 0
fi

# Identifying operating system
if ! [ "$(uname)" == "Linux" ]; then
  echo "This operating system is NOT a Linux distribution!"
  exit 1
fi

run_as_root

# Printing message
echo "Do you want to uninstall systemd? [DANGEROUS!] (yes/no)"
read -r answer

if ! [ "$answer" == "yes" ]; then
    exit 0
fi

# Printing warning message
echo "In some distros it is an essential component, and uninstalling it may result in system damage."
echo "This should NOT be done unless you know exactly what you are doing!"
phrase='Yes, do as I say!'
echo "To continue type in the phrase '$phrase'"
read -r answer

if [ "$phrase" == "$answer" ]; then
  uninstall_on_linux
fi