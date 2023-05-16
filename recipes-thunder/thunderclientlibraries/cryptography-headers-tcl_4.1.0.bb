SUMMARY = "WPEFramework cryptography client libraries - headers"
LICENSE = "Apache-2.0"
PR = "r0"

require include/thunder_clientlibraries_${PV}.inc

# this is a HAL package only, nothing to build
do_compile[noexec] = "1"
do_configure[noexec] = "1"

FILESEXTRAPATHS_prepend = "${THISDIR}/cryptography-headers-tcl-${PV}:"
SRC_URI += "file://0001-pragma-to-ifndef.patch"

# also get rid of the default dependency added in bitbake.conf
# since there is no 'main' package generated (empty)
RDEPENDS_${PN}-dev = ""
# to include the headers in the SDK
ALLOW_EMPTY_${PN} = "1"

do_install() {
    install -m 0755 -d ${D}${includedir}/rdk/tcl/crypto/
    install -m 0644 ${WORKDIR}/git/Source/cryptography/cryptography_vault_ids.h ${D}${includedir}/rdk/tcl/crypto/
    install -m 0644 ${WORKDIR}/git/Source/cryptography/implementation/*.h ${D}${includedir}/rdk/tcl/crypto/
}
