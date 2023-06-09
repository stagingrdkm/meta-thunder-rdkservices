# Snapshot only works on BRCM STBs and RPIs
WPE_SNAPSHOT ?= ""
WPE_SNAPSHOT_rpi = "${@bb.utils.contains('DISTRO_FEATURES', 'vc4graphics', '', 'snapshot', d)}"
WPE_SNAPSHOT_nexus = "snapshot"
WPE_SNAPSHOT_DEP = "${@bb.utils.contains('PREFERRED_PROVIDER_virtual/egl', 'broadcom-refsw', 'broadcom-refsw', bb.utils.contains('PREFERRED_PROVIDER_virtual/egl', 'broadcom-ursr', 'broadcom-refsw', 'userland', d), d)}"

# ----------------------------------------------------------------------------

PACKAGECONFIG[snapshot]       = "-DPLUGIN_SNAPSHOT=ON,-DPLUGIN_SNAPSHOT=OFF,${WPE_SNAPSHOT_DEP} libpng"

# ----------------------------------------------------------------------------
