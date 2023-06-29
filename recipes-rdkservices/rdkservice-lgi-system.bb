SUMMARY = "RDK services LGI System plugin"

PROVIDES += "rdkservice-system"
RPROVIDES_${PN} += "rdkservice-system"

# plugin directory in rdkservices repository
PLUGINDIR="LgiSystemServices"

require rdkservices-common/common_plugin.inc

EXTRA_OECMAKE += "-DPLUGIN_LGISYSTEMSERVICE_AUTOSTART=true"

