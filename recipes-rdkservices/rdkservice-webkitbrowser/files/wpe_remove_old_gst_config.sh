#!/bin/sh
CURRENT_CONFIG="/mnt/wpe_cache/gst_registry_wpe2_22_autoupdated.bin"
for f in /mnt/wpe_cache/*
do
  if [[ $(/usr/bin/expr match $f '/mnt/wpe_cache/gst_registry.*\.bin') != 0 ]]
  then
    if [[ $f != $CURRENT_CONFIG ]]
    then
     echo "r $f" | /bin/systemd-tmpfiles --remove /dev/stdin
    fi
  fi
done
