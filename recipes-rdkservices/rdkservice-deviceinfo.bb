SUMMARY = "RDK services DeviceInfo plugin"

# plugin directory in rdkservices repository
PLUGINDIR="DeviceInfo"

require rdkservices-common/common_plugin.inc

EXTERNALSRC = ""
SRC_URI += "${DEFAULT_NEXT_SRC_URI}"
SRCREV_${PN}="${DEFAULT_NEXT_SRC_URI_REVISION}"

#temporary - POC. Lack of serial number.
SRC_URI += "file://0001-ONEM-30851-integrate-DeviceInfo-with-Thunder-R4.patch"

PACKAGECONFIG ?= "deviceinfo"
PACKAGECONFIG[deviceinfo]   = "-DPLUGIN_DEVICEINFO=ON,-DPLUGIN_DEVICEINFO=OFF,"

EXTRA_OECMAKE += " \
    -DPLUGIN_DEVICEINFO_AUTOSTART=true \
    -DPLUGIN_DEVICEINFO=true \
    -DUSE_THUNDER_R4=true \
 "

RDEPENDS_${PN} += "devicesettings"
DEPENDS += "devicesettings iarmmgrs"

