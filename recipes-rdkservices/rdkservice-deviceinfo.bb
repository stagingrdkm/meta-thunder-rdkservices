SUMMARY = "RDK services DeviceInfo plugin"

# plugin directory in rdkservices repository
PLUGINDIR="DeviceInfo"

require rdkservices-common/common_plugin.inc

EXTRA_OECMAKE += " \
    -DPLUGIN_DEVICEINFO_AUTOSTART=true \
    -DPLUGIN_DEVICEINFO=true \
 "

DEPENDS += "devicesettings iarmmgrs"
RDEPENDS_${PN} += "devicesettings"
