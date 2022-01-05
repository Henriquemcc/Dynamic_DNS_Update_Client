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

# Uninstalls java on linux RPM distros.
function uninstall_java_on_rpm_distros() {
  dnf autoremove java-1.8.0-openjdk
  dnf autoremove java-11-openjdk
  dnf autoremove java-latest-openjdk
}

# Uninstalls java on linux deb distros.
function uninstall_java_on_deb_distros() {
  apt autoremove openjdk-8-jre
  apt autoremove openjdk-11-jre
  apt autoremove openjdk-13-jre
  apt autoremove openjdk-16-jre
  apt autoremove openjdk-17-jre
}

# Uninstalls java on archlinux distros.
function uninstall_java_on_archlinux_distros() {
  pacman -R jre8-openjdk
  pacman -R jre11-openjdk
  pacman -R jre-openjdk
}

# Uninstalls java on linux RPM-ostree distros.
function uninstall_java_on_rpm_os_tree_distros() {
    rpm-ostree uninstall java-1.8.0-openjdk
    rpm-ostree uninstall java-11-openjdk
    rpm-ostree uninstall java-latest-openjdk
}

function uninstall_on_linux() {
  if [ "$(command -v dnf)" ]; then
    uninstall_java_on_rpm_distros
  elif [ "$(command -v apt)" ]; then
    uninstall_java_on_deb_distros
  elif [ "$(command -v pacman)" ]; then
    uninstall_java_on_archlinux_distros
  elif [ "$(command -v rpm-ostree)" ]; then
    uninstall_java_on_rpm_os_tree_distros
  fi
}

# Checking if java is installed
if ! [ "$(command -v java)" ]; then
  exit 0
fi

# Identifying operating system
if ! [ "$(uname)" == "Linux" ]; then
  echo "This operating system is NOT a Linux distribution!"
  exit 1
fi

run_as_root

# Printing message
echo "Do you want to uninstall java? (yes/no)"
echo "Some applications may not run without Java."
read -r answer

if ! [ "$answer" == "yes" ]; then
    exit 0
fi

uninstall_on_linux