SUMMARY = "additional/all rdkservices added by LGI"
inherit packagegroup

PACKAGES = "packagegroup-thunder-lgi-rdkservices"

RDEPENDS_${PN} += "rdkservice-lgidisplaysettings"
RDEPENDS_${PN} += "rdkservice-lgihdcpprofile"
RDEPENDS_${PN} += "rdkservice-lgi-hdmicec"
