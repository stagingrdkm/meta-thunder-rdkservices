#!/bin/sh
# parameters:
# (1) container name
# (2) max. number seconds to wait for self-stop

. /usr/bin/lxc_log_level.inc

echo "Waiting for self-stop of rdkservice container:$1 timeout:$2"
/usr/bin/lxc-wait --name $1 --timeout $2 --state STOPPED /dev/null 2>&1
if [ $? -eq 0 ]
then
    echo "rdkservice container:$1 stopped"
    exit 0
else
    echo "Executing force stop for rdkservice container:$1"
    /usr/bin/lxc-stop -n $1 -o none  -l ${LXC_LOG_LEVEL}
fi

