#!/bin/bash

service_name="dynamic-dns-update-client.service"
service_file_path="/etc/systemd/system/${service_name}"

# Disabling and stopping systemd service
systemctl disable --now "$service_name"

# Erasing systemd service
rm "$service_file_path"