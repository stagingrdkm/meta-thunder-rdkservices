#!/bin/sh

# it looks a bit tricky, but to re-use DeviceIdentification/Implementation/Broadcom/Broadcom.cpp
# the following file is prepared only to align expected format (our /proc/brcm/platform does not contain all the data)
echo "CHIPID[`/usr/bin/getserialno`]" > /tmp/wpeframework/device_identification.txt
echo "Nexus Release `/bin/cat /etc/version`" >> /tmp/wpeframework/device_identification.txt
echo "Chip ID BCM`/bin/cat /sys/devices/soc0/soc_id`" >> /tmp/wpeframework/device_identification.txt
