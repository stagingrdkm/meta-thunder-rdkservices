SUMMARY = "common base of minimum rdkservices used on all RDK builds"
inherit packagegroup

PACKAGES = "packagegroup-thunder-base-rdkservices"

RDEPENDS_${PN} += "${@bb.utils.contains_any('DISTRO_FEATURES', 'thunder-4.3 thunder-4.2', 'rdkservice-messagecontrol', 'rdkservice-tracecontrol', d)}"
RDEPENDS_${PN} += "rdkservice-ocdm"
RDEPENDS_${PN} += "rdkservice-webkitbrowser"
