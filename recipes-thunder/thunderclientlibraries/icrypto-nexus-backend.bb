require ../include/thunder.inc

LICENSE = "Apache-2.0"
PR = "r0"

# TODO: This is a temporary workaround to avoid QA warnings
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

FILESEXTRAPATHS_prepend = "${THISDIR}/icrypto-backend:"

DEPENDS = "cryptography-headers-tcl"

DEPENDS += " \
    wpeframework-interfaces \
    wpeframework-tools-native \
"

RDEPENDS_${PN}_append_dunfell += " wpeframework"

SRC_URI = "${CMF_GIT_ROOT}/soc/broadcom/components/rdkcentral/thunder/Icrypto-nexus;protocol=https;rev=0193acbbbaff02113b2ef018703e5e0ec0f3b40e"
SRC_URI += "file://0001-split-nexus-backend.patch"
SRC_URI += "file://0002-as-shared-lib.patch"
SRC_URI += "file://0003-visible-functions.patch"

S = "${WORKDIR}/git"

do_install () {
    install -m755 -d ${D}${libdir}/
    install -m644 ${B}/libimplementation.so ${D}${libdir}/
}

FILES_${PN}-staticdev = "${libdir}/*.so"
