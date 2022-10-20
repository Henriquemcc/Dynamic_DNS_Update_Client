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

# Uninstalls curl on linux RPM distros.
function uninstall_curl_on_rpm_distros() {

  if [ "$(command -v dnf)" ]; then
    dnf autoremove curl
  fi

  if [ "$(command -v yum)" ]; then
    yum autoremove curl
  fi
}

# Uninstalls curl on linux deb distros.
function uninstall_curl_on_deb_distros() {

  if [ "$(command -v apt)" ]; then
    apt autoremove curl
  fi

  if [ "$(command -v apt-get)" ]; then
    apt-get autoremove curl
  fi
}

# Uninstalls curl on archlinux distros.
function uninstall_curl_on_archlinux_distros() {
  pacman -R curl
}

# Uninstalls curl on linux RPM-ostree distros.
function uninstall_curl_on_rpm_os_tree_distros() {
  rpm-ostree uninstall curl
}

function uninstall_on_linux() {
  if [ "$(command -v dnf)" ] || [ "$(command -v yum)" ]; then
    uninstall_curl_on_rpm_distros
  fi

  if [ "$(command -v apt)" ] || [ "$(command -v apt-get)" ]; then
    uninstall_curl_on_deb_distros
  fi

  if [ "$(command -v pacman)" ]; then
    uninstall_curl_on_archlinux_distros
  fi

  if [ "$(command -v rpm-ostree)" ]; then
    uninstall_curl_on_rpm_os_tree_distros
  fi
}

# Checking if curl is installed
if ! [ "$(command -v curl)" ]; then
  exit 0
fi

# Identifying operating system
if ! [ "$(uname)" == "Linux" ]; then
  echo "This operating system is NOT a Linux distribution!"
  exit 1
fi

run_as_root

# Printing message
echo "Do you want to uninstall curl? (yes/no)"
echo "Some applications may not run without curl."
read -r answer

if ! [ "$answer" == "yes" ]; then
  exit 0
fi

uninstall_on_linux
