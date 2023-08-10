SUMMARY = "RDK services HdcpProfile plugin (LGI addons)"

# plugin directory in rdkservices repository
PLUGINDIR="LgiHdcpProfile"

EXTRA_OECMAKE += " \
    -DPLUGIN_LGIHDCPPROFILE_AUTOSTART=true \
    -DPLUGIN_LGIHDCPPROFILE_OUTOFPROCESS=false \
 "

include rdkservices-common/common_plugin.inc

DEPENDS += "wpeframework-clientlibraries"

RDEPENDS_${PN} += "devicesettings"
