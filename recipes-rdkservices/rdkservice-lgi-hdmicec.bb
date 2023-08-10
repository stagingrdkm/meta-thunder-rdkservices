SUMMARY = "LGI RDK services HdmiCec plugin"

# plugin directory in rdkservices repository
PLUGINDIR="LgiHdmiCec"

include rdkservices-common/common_plugin.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

EXTRA_OECMAKE += " \
    -DPLUGIN_LGIHDMICEC_AUTOSTART=true \
    -DPLUGIN_LGIHDMICEC_OUTOFPROCESS=false \
 "

DEPENDS += "wpeframework-clientlibraries hdmicec"
RDEPENDS_${PN} += "devicesettings"
