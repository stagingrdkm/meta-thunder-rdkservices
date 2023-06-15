SUMMARY = "common base of minimum rdkservices used on all RDK builds"
inherit packagegroup

PACKAGES = "packagegroup-thunder-base-rdkservices"

#RDEPENDS_${PN} += "rdkservice-tracecontrol"
RDEPENDS_${PN} += "rdkservice-ocdm"
RDEPENDS_${PN} += "rdkservice-webkitbrowser"
