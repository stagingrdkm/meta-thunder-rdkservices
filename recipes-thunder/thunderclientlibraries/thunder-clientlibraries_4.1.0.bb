SUMMARY = "WPEFramework client libraries"
LICENSE = "Apache-2.0"
PR = "r0"

PROVIDES += "wpeframework-clientlibraries"
RPROVIDES_${PN} += "wpeframework-clientlibraries"

RDEPENDS_${PN} += "cryptography-tcl"
RDEPENDS_${PN} += "securityagent-tcl"
RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'opencdm', 'ocdm-tcl', '', d)}"

DEPENDS += "cryptography-tcl"
DEPENDS += "securityagent-tcl"
DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'opencdm', 'ocdm-tcl', '', d)}"

# we do not use and build provisionproxy and compositorclient
#require ../include/compositor.inc
#DEPENDS = "${@bb.utils.contains('DISTRO_FEATURES', 'compositor', '${WPE_COMPOSITOR_DEP}', '', d)}"
#PACKAGECONFIG_append = " ${@bb.utils.contains('DISTRO_FEATURES', 'compositor', 'compositorclient', '', d)}"
#PACKAGECONFIG[compositorclient] = "-DCOMPOSITORCLIENT=ON,-DCOMPOSITORCLIENT=OFF"
#PACKAGECONFIG[provisionproxy]   = "-DPROVISIONPROXY=ON,-DPROVISIONPROXY=OFF,libprovision"
#${@bb.utils.contains('DISTRO_FEATURES', 'provisioning', 'provisionproxy', '', d)}
#EXTRA_OECMAKE += "-DPLUGIN_COMPOSITOR_SUB_IMPLEMENTATION=${WPE_COMPOSITOR_SUB_IMPL}"
#RDEPENDS_${PN}_append_rpi = " ${@bb.utils.contains('MACHINE_FEATURES', 'vc4graphics', '', 'userland', d)}"

# it could be sth like:
#RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'provisioning', 'provisionproxy-tcl', '', d)}"
#RDEPENDS_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'compositor', 'compositorclient-tcl', '', d)}"

LICENSE = "CLOSED"
SRC_URI = "file://clientlibraries.txt"

do_install_append() {
    install -d ${D}${sysconfdir}/wpeframework
    install -m 0644 ${WORKDIR}/clientlibraries.txt ${D}${sysconfdir}/wpeframework
}


