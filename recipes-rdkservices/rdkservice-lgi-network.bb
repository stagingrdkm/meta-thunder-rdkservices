SUMMARY = "RDK services Network plugin (LGI Version)"

PROVIDES += "rdkservice-network"
RPROVIDES_${PN} += "rdkservice-network"

# plugin directory in rdkservices repository
PLUGINDIR="LgiNetwork"

require rdkservices-common/common_plugin.inc
OECMAKE_TARGET_COMPILE = "WPEFrameworkNetwork"

EXTRA_OECMAKE  += "-DPLUGIN_LGINETWORK=ON "

DEPENDS += "glib-2.0"
