SUMMARY = "RDK services PlayerInfo plugin"

# plugin directory in rdkservices repository
PLUGINDIR="PlayerInfo"

require rdkservices-common/common_plugin.inc

EXTRA_OECMAKE += " \
    -DPLUGIN_PLAYERINFO_AUTOSTART=true \
 "

PACKAGECONFIG[playerinfo] = "-DPLUGIN_PLAYERINFO=ON -DUSE_DEVICESETTINGS=1,-DPLUGIN_PLAYERINFO=OFF,"
PACKAGECONFIG ?= "playerinfo"

# it is not "a real" gstreamer dependency for us - CMake is written in a way that search for gstreamer and then if found use devicesettings
DEPENDS += "gstreamer1.0 devicesettings iarmmgrs"
RDEPENDS_${PN} += "devicesettings"
