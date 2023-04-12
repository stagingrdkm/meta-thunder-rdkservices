#!/bin/sh
export DBUS_SESSION_BUS_ADDRESS=unix:path=/var/run/dbus/system_bus_socket
export PATH=$PATH:/usr/bin
export nexus_ipc_dir=/var/run/nxserver

exec /usr/bin/WPEProcess "$@"
