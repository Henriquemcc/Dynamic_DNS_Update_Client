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

# Installs java on linux RPM distros.
function install_java_on_rpm_distros() {
  {
    # Trying to install java 8
    dnf install java-1.8.0-openjdk --assumeyes
  } || {
    # Trying to install java 11
    dnf install java-11-openjdk --assumeyes
  } || {
    # Trying to install java latest version
    dnf install java-latest-openjdk --assumeyes
  }
}

# Installs java on linux deb distros.
function install_java_on_deb_distros() {

  # Updating sources
  apt update

  {
    # Trying to install java 8
    apt install openjdk-8-jre --yes
  } || {
    # Trying to install java 11
    apt install openjdk-11-jre --yes
  } || {
    # Trying to install java 13
    apt install openjdk-13-jre --yes
  } || {
    # Trying to install java 16
    apt install openjdk-16-jre --yes
  } || {
    # Trying to install java 17
    apt install openjdk-17-jre --yes
  }
}

# Installs java on archlinux distros.
function install_java_on_archlinux_distros() {
  {
    # Trying to install java 8
    pacman -S jre8-openjdk --noconfirm
  } || {
    # Trying to install java 11
    pacman -S jre11-openjdk --noconfirm
  } || {
    # Trying to install java latest
    pacman -S jre-openjdk --noconfirm
  }
}

# Installs dnf package manager.
function install_dnf_package_manager() {
  yum install dnf --assumeyes
}

# Installs java on linux RPM-ostree distros.
function install_java_on_rpm_os_tree_distros() {
  {
    # Trying to install java 8
    rpm-ostree install java-1.8.0-openjdk --assumeyes
  } || {
    # Trying to install java 11
    rpm-ostree install java-11-openjdk --assumeyes
  } || {
    # Trying to install java latest version
    rpm-ostree install java-latest-openjdk --assumeyes
  }
}

function install_on_linux() {
  if [ "$(command -v dnf)" ]; then
    install_java_on_rpm_distros
  elif [ "$(command -v apt)" ]; then
    install_java_on_deb_distros
  elif [ "$(command -v pacman)" ]; then
    install_java_on_archlinux_distros
  elif [ "$(command -v yum)" ]; then
    install_dnf_package_manager
    install_java_on_rpm_distros
  elif [ "$(command -v rpm-ostree)" ]; then
    install_java_on_rpm_os_tree_distros
  fi
}

# Checking if java is installed
if [ "$(command -v java)" ]; then
    exit 0
fi

run_as_root

# Identifying operating system
if ! [ "$(uname)" == "Linux" ]; then
  echo "This operating system is NOT a Linux distribution!"
  exit 1
fi

install_on_linux
