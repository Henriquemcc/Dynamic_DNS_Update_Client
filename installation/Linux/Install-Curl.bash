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

function install_on_linux() {

  # linux RPM distros
  if [ "$(command -v dnf)" ]; then
    dnf install curl --assumeyes
  fi

  # linux deb distros
  if [ "$(command -v apt)" ]; then
    apt update
    apt install curl --yes
  fi

  # linux deb distros
  if [ "$(command -v apt-get)" ]; then
    apt-get update
    apt-get install curl --yes
  fi

  # archlinux distros
  if [ "$(command -v pacman)" ]; then
    pacman -S curl --noconfirm
  fi

  # linux RPM distros
  if [ "$(command -v yum)" ]; then
    yum install curl --assumeyes
  fi

  # linux RPM-ostree distros
  if [ "$(command -v rpm-ostree)" ]; then
    rpm-ostree install curl --assumeyes
  fi
}

# Checking if curl is installed
if [ "$(command -v curl)" ]; then
  exit 0
fi

# Running as root
run_as_root

# Identifying operating system
if ! [ "$(uname)" == "Linux" ]; then
  echo "This operating system is NOT a Linux distribution!"
  exit 1
fi

install_on_linux
