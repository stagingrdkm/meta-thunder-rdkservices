SUMMARY = "RDK services HdmiCec plugin"

# plugin directory in rdkservices repository
# integrated HdmiCec, it looks like one from pair org.rdk.HdmiCec org.rdk.HdmiCec_2 should be integrated
PLUGINDIR="HdmiCec"

require rdkservices-common/common_plugin.inc

SRC_URI += "file://HdmiCecConfig.json"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

EXTRA_OECMAKE += " \
    -DPLUGIN_HDMICEC_AUTOSTART=true \
 "

CXXFLAGS += "-DLGI_CUSTOM_IMPL"

DEPENDS += "hdmicec"
RDEPENDS_${PN} += "devicesettings"

do_install_append() {
    install -m 0400 ${WORKDIR}/HdmiCecConfig.json ${D}${sysconfdir}/WPEFramework/plugins/
}
