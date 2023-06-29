SUMMARY = "WPEFramework client libraries securityagent and securityutility"
LICENSE = "Apache-2.0"
PR = "r0"

require include/thunder_clientlibraries_${PV}.inc

SRC_URI += "file://0001-RDK-28534-Security-Agent-Utility-and-Logging-ClientLibs.patch"

PACKAGECONFIG ?= "securityagent securityutility"
PACKAGECONFIG[securityagent]    = "-DSECURITYAGENT=ON, -DSECURITYAGENT=OFF"
PACKAGECONFIG[securityutility]  = "-DSECURITYUTILITY=ON, -DSECURITYUTILITY=OFF"

do_install_append() {
    install -m 0644 ${D}${libdir}/pkgconfig/WPEFrameworkSecurityAgent.pc ${D}${libdir}/pkgconfig/securityagent.pc
}
