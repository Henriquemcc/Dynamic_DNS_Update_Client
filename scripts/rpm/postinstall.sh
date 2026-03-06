#!/bin/bash

service_name="dynamic-dns-update-client.service"
service_file_path="/etc/systemd/system/${service_name}"
jar_download_destination="/opt/dduc/app.jar"

# Creating a Systemd service
{
  echo "[Unit]"
  echo "Description=Dynamic dns update client service"
  echo "After=network.target"
  echo "StartLimitIntervalSec=0"
  echo
  echo "[Service]"
  echo "Type=simple"
  echo "Restart=always"
  echo "RestartSec=1"
  echo "User=root"
  echo "ExecStart=/usr/bin/java -jar ${jar_download_destination} daemon"
  echo
  echo "[Install]"
  echo "WantedBy=multi-user.target"
} > "$service_file_path"

# Enabling and starting service
systemctl enable --now "$service_name"