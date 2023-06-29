SUMMARY = "common base of minimum rdkservices used on all RDK builds"
inherit packagegroup

PACKAGES = "packagegroup-thunder-base-rdkservices"

RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'thunder-4.3', '', 'rdkservice-tracecontrol', d)}"
RDEPENDS_${PN} += "rdkservice-ocdm"
RDEPENDS_${PN} += "rdkservice-webkitbrowser"
