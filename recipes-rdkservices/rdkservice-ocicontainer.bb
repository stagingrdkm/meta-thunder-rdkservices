SUMMARY = "RDK services OCIContainer plugin"

# plugin directory in rdkservices repository
PLUGINDIR="OCIContainer"

include rdkservices-common/common_plugin.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

## patches and files from rdkservice-ocicontainer
SRC_URI += "file://CMakeLists.txt;subdir=git/"

DEPENDS += "wpeframework-clientlibraries dobby omi devicesettings iarmbus iarmmgrs-hal-headers rfc"
