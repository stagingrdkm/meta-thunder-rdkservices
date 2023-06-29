SUMMARY = "RDK services DeviceIdentification plugin"

# plugin directory in rdkservices repository
PLUGINDIR="DeviceIdentification"

require rdkservices-common/common_plugin.inc

EXTRA_OECMAKE += " \
    -DPLUGIN_DEVICEIDENTIFICATION_AUTOSTART=true \
"

