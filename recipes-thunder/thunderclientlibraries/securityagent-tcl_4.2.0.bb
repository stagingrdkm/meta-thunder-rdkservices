SUMMARY = "WPEFramework client libraries securityagent and securityutility"
LICENSE = "Apache-2.0"
PR = "r0"
do_compile[lockfiles] = "${TMPDIR}/rdkservice.lock"
require include/thunder_clientlibraries_${PV}.inc

# that patch is like the original one from Comcast, but link with orginal library name from repository
SRC_URI += "file://0001-RDK-28534-Security-Agent-Utility-and-Logging-ClientLibs.patch"
# I really do not understand whatfor change the library name (not applied), we really could use original name, moreover it follow some naming convention "libWPEFrameworkSecurityAgent" like the other libraries
# SRC_URI += "file://0004-R4-Security-Agent-Library-NameChange.patch"

PACKAGECONFIG ?= "securityagent securityutility"
PACKAGECONFIG[securityagent]    = "-DSECURITYAGENT=ON, -DSECURITYAGENT=OFF"
PACKAGECONFIG[securityutility]  = "-DSECURITYUTILITY=ON, -DSECURITYUTILITY=OFF"

do_install_append() {
    install -m 0644 ${D}${libdir}/pkgconfig/WPEFrameworkSecurityAgent.pc ${D}${libdir}/pkgconfig/securityagent.pc
}
