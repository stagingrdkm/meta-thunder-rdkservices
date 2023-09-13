SUMMARY = "all required rdkservices as defined in fireboltv0.6 interface"
inherit packagegroup

PACKAGES = "packagegroup-thunder-firebolt-rdkservices"

RDEPENDS_${PN} += "rdkservice-displayinfo"
RDEPENDS_${PN} += "rdkservice-playerinfo"
RDEPENDS_${PN} += "rdkservice-hdcpprofile"
RDEPENDS_${PN} += "${@bb.utils.contains_any('DISTRO_FEATURES', 'thunder-4.3 thunder-4.2', 'rdkservice-deviceinfo', '', d)}"
