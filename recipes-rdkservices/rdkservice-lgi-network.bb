SUMMARY = "RDK services Network plugin (LGI Version)"

PROVIDES += "rdkservice-network"
RPROVIDES_${PN} += "rdkservice-network"

# plugin directory in rdkservices repository
PLUGINDIR="LgiNetwork"

include rdkservices-common/common_plugin.inc
OECMAKE_TARGET_COMPILE = "WPEFrameworkNetwork"

EXTERNALSRC = ""
SRC_URI += "${DEFAULT_NEXT_SRC_URI}"
SRCREV_${PN}="${DEFAULT_NEXT_SRC_URI_REVISION}"

EXTRA_OECMAKE  += "-DPLUGIN_LGINETWORK=ON "

DEPENDS += "glib-2.0"
