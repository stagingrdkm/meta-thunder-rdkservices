SUMMARY = "RDK services OCIContainer plugin"

# plugin directory in rdkservices repository
PLUGINDIR="OCIContainer"

require rdkservices-common/common_plugin.inc

EXTERNALSRC = ""
SRC_URI += "${DEFAULT_NEXT_SRC_URI}"
SRCREV_${PN}="${DEFAULT_NEXT_SRC_URI_REVISION}"


DEPENDS += "wpeframework-clientlibraries dobby omi devicesettings iarmbus iarmmgrs-hal-headers rfc"
