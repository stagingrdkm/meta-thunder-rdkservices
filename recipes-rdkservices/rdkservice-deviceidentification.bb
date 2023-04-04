SUMMARY = "RDK services DeviceIdentification plugin"

# plugin directory in rdkservices repository
PLUGINDIR="DeviceIdentification"

include rdkservices-common/common_plugin.inc

EXTERNALSRC = ""
SRC_URI += "${DEFAULT_NEXT_SRC_URI}"
SRCREV_${PN}="${DEFAULT_NEXT_SRC_URI_REVISION}"

EXTRA_OECMAKE += " \
    -DPLUGIN_DEVICEIDENTIFICATION_AUTOSTART=true \
"

