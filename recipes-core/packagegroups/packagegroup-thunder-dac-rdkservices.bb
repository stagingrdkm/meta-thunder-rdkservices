SUMMARY = "DAC required rdkservices"
inherit packagegroup

PACKAGES = "packagegroup-thunder-dac-rdkservices"

RDEPENDS_${PN} += "rdkservice-lisa"
#RDEPENDS_${PN} += "rdkservice-fireboltmediaplayer"
RDEPENDS_${PN} += "rdkservice-ocicontainer"
