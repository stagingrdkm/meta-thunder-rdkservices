# compilation issues around devicesettings dependencies

SUMMARY = "RDK services WifiManager plugin"

# plugin directory in rdkservices repository
PLUGINDIR="WifiManager"
do_compile[lockfiles] = "${TMPDIR}/rdkservice.lock"
require rdkservices-common/common_plugin.inc

EXTRA_OECMAKE += " \
    -DPLUGIN_WIFI_AUTOSTART=true \
    -DPLUGIN_WIFI_IMPL=impl_lg \
 "

# RDEPENDS_${PN} += "devicesettings"
DEPENDS += "glib-2.0 iarmmgrs"
