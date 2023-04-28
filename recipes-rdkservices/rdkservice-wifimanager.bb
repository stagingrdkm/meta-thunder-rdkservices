# compilation issues around devicesettings dependencies

SUMMARY = "RDK services WifiManager plugin"

# plugin directory in rdkservices repository
PLUGINDIR="WifiManager"

include rdkservices-common/common_plugin.inc

EXTERNALSRC = ""
SRC_URI += "${DEFAULT_NEXT_SRC_URI}"
SRCREV_${PN}="${DEFAULT_NEXT_SRC_URI_REVISION}"

EXTRA_OECMAKE += " \
    -DPLUGIN_WIFI_AUTOSTART=true \
    -DPLUGIN_WIFI_IMPL=impl_lg \
 "

# RDEPENDS_${PN} += "devicesettings"
DEPENDS += "glib-2.0"
