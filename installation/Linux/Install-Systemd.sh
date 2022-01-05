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

# Installs systemd on rpm distros.
function install_systemd_on_rpm_distros() {
  dnf install systemd --assumeyes
}

# Installs systemd on deb distros.
function install_systemd_on_deb_distros() {

  # Updating sources
  apt update

  # Installing Systemd
  apt install systemd --yes
}

# Installs systemd on archlinux distros.
function install_systemd_on_archlinux_distros() {
  pacman -Sy systemd --noconfirm
}

# Installs dnf package manager.
function install_dnf_package_manager() {
  yum install dnf --assumeyes
}

# Installs systemd on rpm-ostree distros.
function install_systemd_on_rpm_os_tree_distros() {
  rpm-ostree install systemd --assumeyes
}

function install_on_linux() {
  if [ "$(command -v dnf)" ]; then
    install_systemd_on_rpm_distros
  elif [ "$(command -v apt)" ]; then
    install_systemd_on_deb_distros
  elif [ "$(command -v pacman)" ]; then
    install_systemd_on_archlinux_distros
  elif [ "$(command -v yum)" ]; then
    install_dnf_package_manager
    install_systemd_on_rpm_distros
  elif [ "$(command -v rpm-ostree)" ]; then
    install_systemd_on_rpm_os_tree_distros
  fi
}

# Checking if systemd is installed
if [ "$(command -v systemctl)" ]; then
    exit 0
fi

run_as_root

# Identifying operating system
if [ "$(uname)" == "Linux" ]; then
  install_on_linux
else
  exit 1
fi