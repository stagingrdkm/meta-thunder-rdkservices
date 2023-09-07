SUMMARY = "RDK services DeviceInfo plugin"

# plugin directory in rdkservices repository
PLUGINDIR="DeviceInfo"

require rdkservices-common/common_plugin.inc

EXTRA_OECMAKE += " \
    -DPLUGIN_DEVICEINFO_AUTOSTART=true \
    -DPLUGIN_DEVICEINFO=true \
 "

include ${@bb.utils.contains('DISTRO_FEATURES', 'thunder-4.2', 'rdkservice-deviceinfo/deviceinfo-4.2.inc', '', d)}

DEPENDS += "devicesettings iarmmgrs"
RDEPENDS_${PN} += "devicesettings"
