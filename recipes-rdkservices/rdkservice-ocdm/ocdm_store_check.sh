#!/bin/sh
# license store starts with 1MB. Approx it should fit for 2000 licenses. If it gets too small, it size is doubled, to 2101248 B. If store is getting even bigger, we delete it completely.
# Very likely it might be the side effect of using unmanaged application storing a lot of persistent, long term licenses.
/usr/bin/find /mnt/drm_storage/wpeframework/OCDM/drmstore -size +2101248c -exec /bin/echo "r /mnt/drm_storage/wpeframework/OCDM/drmstore - - - -" \; | /bin/systemd-tmpfiles --remove /dev/stdin
