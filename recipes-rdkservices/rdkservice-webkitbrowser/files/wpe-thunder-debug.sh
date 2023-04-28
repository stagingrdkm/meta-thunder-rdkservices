#!/bin/sh

if [ -f /mnt/wpe_cache/wpe.dbg ]; then
 . /mnt/wpe_cache/wpe.dbg
fi

exec /usr/bin/WPEProcess "$@"
