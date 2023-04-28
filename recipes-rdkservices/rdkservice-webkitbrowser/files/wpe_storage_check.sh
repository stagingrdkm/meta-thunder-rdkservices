#!/bin/sh
echo "d /tmp/WebKitBrowser 770 wpe wpe" | /bin/systemd-tmpfiles --create /dev/stdin
