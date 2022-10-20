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

  if [ "$(command -v dnf)" ]; then
    dnf install systemd --assumeyes
  fi

  if [ "$(command -v yum)" ]; then
    yum install systemd --assumeyes
  fi
}

# Installs systemd on deb distros.
function install_systemd_on_deb_distros() {

  if [ "$(command -v apt)" ]; then
    # Updating sources
    apt update

    # Installing Systemd
    apt install systemd --yes
  fi

  if [ "$(command -v apt-get)" ]; then
    # Updating sources
    apt-get update

    # Installing Systemd
    apt-get install systemd --yes
  fi
}

# Installs systemd on archlinux distros.
function install_systemd_on_archlinux_distros() {
  pacman -S systemd --noconfirm
}

# Installs systemd on rpm-ostree distros.
function install_systemd_on_rpm_os_tree_distros() {
  rpm-ostree install systemd --assumeyes
}

function install_on_linux() {
  if [ "$(command -v dnf)" ] || [ "$(command -v yum)" ]; then
    install_systemd_on_rpm_distros
  fi

  if [ "$(command -v apt)" ] || [ "$(command -v apt-get)" ]; then
    install_systemd_on_deb_distros
  fi

  if [ "$(command -v pacman)" ]; then
    install_systemd_on_archlinux_distros
  fi

  if [ "$(command -v rpm-ostree)" ]; then
    install_systemd_on_rpm_os_tree_distros
  fi
}

# Checking if systemd is installed
if [ "$(command -v systemctl)" ]; then
  exit 0
fi

run_as_root

# Identifying operating system
if ! [ "$(uname)" == "Linux" ]; then
  echo "This operating system is NOT a Linux distribution!"
  exit 1
fi

install_on_linux
