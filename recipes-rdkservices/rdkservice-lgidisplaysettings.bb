SUMMARY = "RDK services LgiDisplaySettings plugin"

# plugin directory in rdkservices repository
PLUGINDIR="LgiDisplaySettings"

include rdkservices-common/common_plugin.inc

EXTRA_OECMAKE += " \
    -DPLUGIN_LGIDISPLAYSETTINGS_AUTOSTART=true \
    -DPLUGIN_LGIDISPLAYSETTINGS_OUTOFPROCESS=false \
 "

# as we are compiling sources from two directories DisplaySettings + LgiDisplaySettings
# so we need to override the MODULE_NAME definiton normally provieded in Module.h
TARGET_CFLAGS += "-DMODULE_NAME=Plugin_LgiDisplaySettings -Wno-write-strings"

RDEPENDS_${PN} += "rfc"
RDEPENDS_${PN} += "devicesettings"

DEPENDS += "wpeframework-clientlibraries rfc devicesettings"
