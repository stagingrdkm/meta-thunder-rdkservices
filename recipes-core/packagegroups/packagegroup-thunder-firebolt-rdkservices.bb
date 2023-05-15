SUMMARY = "all required rdkservices as defined in fireboltv0.6 interface"
inherit packagegroup

PACKAGES = "packagegroup-thunder-firebolt-rdkservices"

RDEPENDS_${PN} += "rdkservice-displayinfo"
RDEPENDS_${PN} += "rdkservice-playerinfo"
RDEPENDS_${PN} += "rdkservice-hdcpprofile"

# not yet included
#RDEPENDS_${PN} += "rdkservice-deviceinfo"
